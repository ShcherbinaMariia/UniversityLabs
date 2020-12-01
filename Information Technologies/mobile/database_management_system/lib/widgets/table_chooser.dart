import 'package:database_management_system/backend/backend.dart';
import 'package:database_management_system/widgets/table_view.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class TableChooser extends StatefulWidget {
  TableChooser(this._backend, this._database);
  final Backend _backend;
  final Function _database;

  @override
  State<StatefulWidget> createState() => _TableChooserState();
}

class _TableChooserState extends State<TableChooser> {
  List<String> _tables;
  bool isLoading;
  String _database;
  String _selectedTable;

  DropdownButton getTableChooser() {
    List<DropdownMenuItem<String>> items = this._tables.map<DropdownMenuItem<String>>((String value) {
      return DropdownMenuItem<String>(
        value: value,
        child: Text(value),
      );
    }).toList();

    return DropdownButton<String>(
      value: _selectedTable,
      icon: Icon(Icons.arrow_downward),
      underline: Container(
        height: 2,
        color: Colors.lightBlueAccent,
      ),
      onChanged: (newValue) => {
        setState(() {
          _selectedTable = newValue;
          _fetchData();
        })
      },
      items: items,
    );
  }

  void _fetchData() async {
    setState(() {
      isLoading = true;
    });

    this._tables = await widget._backend.getAvailableTables(widget._database());
    this._database = widget._database();

    setState(() {
      isLoading = false;
    });
  }

  List<Widget> getItems() {
    List<Widget> result = new List();
    result.add(getTableChooser());
    result.add((this._selectedTable == null ? Container() : TableView(widget._backend, widget._database(), _selectedTable)));
    return result;
  }

  @override
  void initState() {
    this._selectedTable = null;
    _fetchData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    if (this._database != widget._database()) {
      this._selectedTable = null;
      _fetchData();
    }

    return Center(child: isLoading ? CircularProgressIndicator() : Column(children: getItems(),));
  }
}