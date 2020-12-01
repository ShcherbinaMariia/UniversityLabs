import 'package:database_management_system/backend/backend.dart';
import 'package:database_management_system/model/attribute_filter.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'form.dart';

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
  Map<String, List<String>> filters;

  void _fetchData() async {
    setState(() {
      isLoading = true;
    });

    this.schema = await widget._backend
        .getTableSchema(widget._selectedDatabase, widget._selectedTable);
    this.filters = await widget._backend
        .getFilters(widget._selectedDatabase, widget._selectedTable);
    this.rows = await widget._backend
        .getTableRows(widget._selectedDatabase, widget._selectedTable);

    setState(() {
      isLoading = false;
    });
  }

  @override
  void initState() {
    super.initState();
    _fetchData();
  }

  void filterTable(List<AttributeFilter> filters) async {
    setState(() {
      isLoading = true;
    });

    this.rows = await widget._backend.getFilteredRows(
        widget._selectedDatabase, widget._selectedTable, filters);

    setState(() {
      isLoading = false;
    });
  }

  Widget getDataTable() {
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
    return Container(
      child: DataTable(columns: columns, rows: rows),
      decoration: BoxDecoration(border: Border.all(color: Colors.grey)),
    );
  }

  void showForm() {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => FilterFormScreen(this.filters, filterTable)));
  }

  @override
  Widget build(BuildContext context) {
    return isLoading
        ? const CircularProgressIndicator()
        : Column(children: [
            getDataTable(),
            ElevatedButton(onPressed: () => showForm(), child: Text("Filter"))
          ]);
  }
}
