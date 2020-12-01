import 'package:database_management_system/model/attribute_filter.dart';
import 'package:http/http.dart' as http;
import 'package:sprintf/sprintf.dart';

const String BASE_URL = "https://database-management-system.herokuapp.com";

const String DATABASES_URL = BASE_URL + "/databases/";
const String TABLES_URL_F = DATABASES_URL + "%s/tables/";
const String SCHEMA_URL_F = TABLES_URL_F + "%s/schema/";
const String ROWS_URL_F = TABLES_URL_F + "%s/rows/";
const String FILTERS_URL_F = SCHEMA_URL_F + "/filters/";
const String FILTER_ROWS_URL_F = ROWS_URL_F + "/filter?%s";

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

  Future<http.Response> getFilters(String database, String table) async {
    return http.get(sprintf(FILTERS_URL_F, [database, table]));
  }

  Future<http.Response> getFilteredRows(String database, String table, List<AttributeFilter> filters) async {
    List<String> params = List();
    filters.forEach((element) {params.add(element.toParamString());});
    return http.get(sprintf(FILTER_ROWS_URL_F, [database, table, params.join("&")]));
  }
}