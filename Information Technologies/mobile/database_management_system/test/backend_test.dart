import 'package:database_management_system/backend/backend.dart';
import 'package:database_management_system/backend/client.dart';
import 'package:mockito/mockito.dart';
import 'package:test/test.dart';
import 'package:http/http.dart' as http;

class MockClient extends Mock implements Client {}

final int statusCode = 200;

Future<http.Response> _fakeResponse(String body) {
  return Future.delayed(Duration(milliseconds: 1), () => new http.Response(body, statusCode));
}

Future<http.Response> _fakeGetAvailableDatabasesResponse() {
  return _fakeResponse('[{"name":"db"}]');
}

Future<http.Response> _fakeGetAvailableTablesResponse() {
  return _fakeResponse('[{"name":"table1"}, {"name":"table2"}]');
}

Future<http.Response> _fakeGetTableSchemaResponse() {
  return _fakeResponse('[{"name":"r", "type":"REAL"}, {"name":"ii", "type":"INTEGER_INTERVAL"}]');
}

void main() {
  MockClient mockClient;
  Backend backend;

  setUp(() {
    mockClient = MockClient();
    backend = new Backend(mockClient);
  });

  group("getAvailableDatabases", () {
    test('return correct result', () async {
      when(mockClient.getAvailableDatabases()).thenAnswer((_) => _fakeGetAvailableDatabasesResponse());

      var availableDatabases = await backend.getAvailableDatabases();

      expect(availableDatabases.length, 1);

      expect(availableDatabases[0], "db");
    });
  });

  group("getAvailableTables", () {
    test('call client.getAvailableTable with right parameters', () async {
      when(mockClient.getAvailableTables(captureAny))
          .thenAnswer((_) => _fakeGetAvailableTablesResponse());

      var _ = await backend.getAvailableTables("db");

      verify(mockClient.getAvailableTables("db")).called(1);
    });

    test('return correct result', () async {
      when(mockClient.getAvailableTables(captureAny)).thenAnswer((_) => _fakeGetAvailableTablesResponse());

      var availableTables = await backend.getAvailableTables("db");

      expect(availableTables.length, 2);

      expect(availableTables[0], "table1");
      expect(availableTables[1], "table2");
    });
  });

  group("getTableSchema", () {
    test('call client.getTableSchema with right parameters', () async {
      when(mockClient.getTableSchema(captureAny, captureAny))
          .thenAnswer((_) => _fakeGetTableSchemaResponse());

      var _ = await backend.getTableSchema("db", "table1");

      verify(mockClient.getTableSchema("db", "table1")).called(1);
    });

    test('return correct result', () async {
      when(mockClient.getTableSchema(captureAny, captureAny)).thenAnswer((_) => _fakeGetTableSchemaResponse());

      var availableTables = await backend.getTableSchema("db", "table1");

      expect(availableTables.length, 2);

      expect(availableTables[0], "r");
      expect(availableTables[1], "ii");
    });
  });
}
