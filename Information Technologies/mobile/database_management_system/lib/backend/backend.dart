import 'package:database_management_system/backend/client.dart';
import 'dart:convert';

import 'package:database_management_system/model/attribute_filter.dart';

class Backend {
  Backend(this.client);
  
  final Client client;

  Future<List<String>> getAvailableDatabases() async {
    var response = await client.getAvailableDatabases();
    List<dynamic> databases = jsonDecode(response.body.toString());
    return extractName(databases);
  }

  Future<List<String>> getAvailableTables(String database) async {
    var response = await client.getAvailableTables(database);
    List<dynamic> tables = jsonDecode(response.body.toString());
    return extractName(tables);
  }

  Future<List<String>> getTableSchema(String database, String table) async {
    var response = await client.getTableSchema(database, table);
    List<dynamic> schema = jsonDecode(response.body.toString());
    return extractName(schema);
  }

  Future<Map<String, List<String>>> getFilters(String database, String table) async {
    var response = await client.getFilters(database, table);
    Map<String, List<String>> result = Map();
    Map<String, dynamic> decoded = jsonDecode(response.body.toString());
    decoded.forEach((key, value) {
      List<String> values = List();
      value.forEach((element) {
        values.add(element);
      });
      result[key] = values;
    });
    return result;
  }

  Future<List<dynamic>> getFilteredRows(String database, String table, List<AttributeFilter> filters) async {
    var response = await client.getFilteredRows(database, table, filters);
    List<dynamic> rows = jsonDecode(response.body.toString());
    return processRows(rows);
  }

  Future<List<dynamic>> getTableRows(String database, String table) async {
    var response = await client.getTableRows(database, table);
    List<dynamic> rows = jsonDecode(response.body.toString());
    return processRows(rows);
  }

  List<String> extractName(List<dynamic> list) {
    List<String> result = new List();
    list.forEach((entry) {
      result.add(entry['name']);
    });
    return result;
  }

  List<dynamic> processRows(List<dynamic> rows) {
    List<dynamic> result = new List();
    rows.forEach((row) {
      result.add(row['values']);
    });
    return result;
  }
}