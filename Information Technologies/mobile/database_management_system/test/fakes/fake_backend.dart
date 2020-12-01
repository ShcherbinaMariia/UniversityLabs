import 'package:database_management_system/backend/backend.dart';
import 'package:mockito/mockito.dart';

class MockBackend extends Mock implements Backend {
  @override
  Future<List<String>> getAvailableTables(String db) async {
    List<String> results = new List();

    results.add("table 1");
    results.add("table 2");

    return Future.delayed(
        Duration(milliseconds: 1), () => results);
  }

  @override
  Future<List<String>> getTableSchema(String database, String table) async {
    List<String> results = new List();

    results.add("real_attribute");
    results.add("string_attribute");
    results.add("integer_interval_attribute");

    return Future.delayed(
        Duration(milliseconds: 1), () => results);
  }

  @override
  Future<List<dynamic>> getTableRows(String database, String table) async {
    List<dynamic> results = new List();

    return results;
  }
}
