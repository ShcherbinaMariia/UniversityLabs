import 'package:http/http.dart' as http;
import 'package:sprintf/sprintf.dart';

const String BASE_URL = "http://192.168.0.194:81";

const String DATABASES_URL = BASE_URL + "/databases/";
const String TABLES_URL_F = DATABASES_URL + "%s/tables/";
const String SCHEMA_URL_F = TABLES_URL_F + "%s/schema/";
const String ROWS_URL_F = TABLES_URL_F + "%s/rows/";

class Client {
  Future<http.Response> getAvailableDatabases() async {
    return http.get(DATABASES_URL);
  }

  Future<http.Response> getAvailableTables(String database) async {
    return http.get(sprintf(TABLES_URL_F, [database]));
  }

  Future<http.Response> getTableSchema(String database, String table) async {
    return http.get(sprintf(SCHEMA_URL_F, [database, table]));
  }

  Future<http.Response> getTableRows(String database, String table) async {
    return http.get(sprintf(ROWS_URL_F, [database, table]));
  }
}