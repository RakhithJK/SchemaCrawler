from __future__ import print_function
import re

print("@startuml")
print("""
!theme plain
hide empty methods

!define schema(name, slug) package "name" as slug <<Rectangle>>
!define table(name, slug) entity "<b>name</b>" as slug << (T, white) table >>
!define view(name, slug) entity "<b>name</b>" as slug << (V, yellow) view >>
!define pk(name) <color:#GoldenRod><&key></color> <b>name</b>
!define fk(name) <color:#Silver><&key></color> name
!define column(name) {field} <color:#White><&media-record></color> name

""")

print('title "' + title + '"')
print('')
print('')

# Tables
for schema in catalog.getSchemas():
    if not catalog.getTables(schema):
        continue
    print('schema(' + re.sub(r'\"', '', schema.fullName)  + ', ' + schema.key().slug() + ') {')
    print('')
    for table in catalog.getTables(schema):
        if not table.tableType.isView():
            print('table', end='')
        else:
            print('view', end='')
        print('(' + re.sub(r'\"', '', table.name) + ', ' + table.key().slug() + ') {')
        for column in table.columns:
            if column.isPartOfPrimaryKey():
                print('  pk', end='')
            elif column.isPartOfForeignKey():
                print('  fk', end='')
            else:
                print('  column', end='')
            print('(' + column.name + '): ' + column.columnDataType.name, end='')
            print(' ', end='')
            if not column.nullable:
                print('NOT NULL', end='')
            print('')
        print('}')
        print('')
        if table.remarks:
            print('note left of ' + table.key().slug() + ' #LemonChiffon')
            print(table.remarks)
            print('end note')
            print('')
        for column in table.columns:
            if column.remarks:
                print('note right of ' + table.key().slug() + '::' + column.name \
                    + ' #WhiteSmoke')
                print(column.remarks)
                print('end note')
                print('')
        print('')
        print('')
    print('')
    print('}')
    print('')
    print('')

# Foreign keys
for table in catalog.tables:
    for fk in table.exportedForeignKeys:
        pkTable = fk.primaryKeyTable
        fkTable = fk.foreignKeyTable
        for columnReference in fk.columnReferences:
            pkColumn = columnReference.primaryKeyColumn
            fkColumn = columnReference.foreignKeyColumn
            print('' \
                  + pkTable.schema.key().slug() + '.'
                  + pkTable.key().slug() + '::'
                  + re.sub(r'\"', '', pkColumn.name)
                  + '  ||--o{ ' \
                  + fkTable.schema.key().slug() + '.'
                  + fkTable.key().slug() + '::'
                  + re.sub(r'\"', '', fkColumn.name)
                  , end='')
            if fk.name and not fk.name.startswith('SCHCRWLR_'):
                print(' : '
                      + fk.name, end='')
            print(' <')
print('')

print("@enduml")
