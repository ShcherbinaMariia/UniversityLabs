import 'package:database_management_system/backend/backend.dart';
import 'package:database_management_system/backend/client.dart';
import 'package:database_management_system/widgets/table_chooser.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    Client client = new Client();
    return MaterialApp(
      title: 'Database Management System',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: HomePage(new Backend(client)),
    );
  }
}

class HomePage extends StatefulWidget {
  HomePage(this._backend);
  final Backend _backend;

  @override
  State<StatefulWidget> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  bool isLoading;
  List<String> databases;
  String _selectedDatabase;

  void _fetchData() async {
    setState(() {
      isLoading = true;
    });

    this.databases = await widget._backend.getAvailableDatabases();

    setState(() {
      isLoading = false;
    });
  }

  @override
  void initState() {
    super.initState();
    _fetchData();
  }

  PopupMenuButton getDatabaseChooser() {
    List<PopupMenuItem<String>> items = this.databases.map<
        PopupMenuItem<String>>((String value) {
      return PopupMenuItem<String>(
        value: value,
        child: Text(value),
      );
    }).toList();

    return PopupMenuButton<String>(
      child: Text(_selectedDatabase == null ? "Select db" : _selectedDatabase),
      tooltip: "select database",
      onSelected: (String newValue) {
        setState(() {
          _selectedDatabase = newValue;
        });
      },
      itemBuilder: (BuildContext context) {
        return items;
      }
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: isLoading ? Text("Loading databases...") : getDatabaseChooser()
      ),
      body: this._selectedDatabase == null ? Container() : TableChooser(this.widget._backend, () => this._selectedDatabase),
    );
  }
}
