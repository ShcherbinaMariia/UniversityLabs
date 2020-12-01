import 'package:database_management_system/backend/backend.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class TableView extends StatefulWidget {
  TableView(this._backend, this._selectedDatabase, this._selectedTable);
  final Backend _backend;
  final String _selectedDatabase;
  final String _selectedTable;

  @override
  _TableViewState createState() => _TableViewState();
}

class _TableViewState extends State<TableView> {
  bool isLoading = true;
  List<String> schema;
  List<dynamic> rows;

  void _fetchData() async {
    setState(() {
      isLoading = true;
    });

    this.schema = await widget._backend.getTableSchema(widget._selectedDatabase, widget._selectedTable);
    this.rows = await widget._backend.getTableRows(widget._selectedDatabase, widget._selectedTable);

    setState(() {
      isLoading = false;
    });
  }

  @override
  void initState() {
    super.initState();
    _fetchData();
  }
  
  DataTable getDataTable() {
    List<DataColumn> columns = new List();
    this.schema.forEach((attribute) {
      columns.add(DataColumn(label: Text(attribute)));
    });

    List<DataRow> rows = new List();
    this.rows.forEach((row) {
      List<DataCell> cells = new List();
      this.schema.forEach((attribute) {
        cells.add(DataCell(Text(row[attribute])));
      });
      rows.add(new DataRow(cells: cells));
    });
    return DataTable(columns: columns, rows: rows);
  }

  @override
  Widget build(BuildContext context) {
    return isLoading ? const CircularProgressIndicator() : getDataTable();
  }
}