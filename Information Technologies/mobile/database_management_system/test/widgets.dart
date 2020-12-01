import 'package:database_management_system/widgets/table_chooser.dart';
import 'package:database_management_system/widgets/table_view.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'fakes/fake_backend.dart';

void main() {
  MockBackend mockBackend;

  setUpAll(() {
    mockBackend = MockBackend();
  });

  testWidgets("table chooser",
          (WidgetTester tester) async {
        await tester.pumpWidget(MaterialApp(home: Scaffold(body: Center(child: TableChooser(mockBackend, () => "db")))));
        await tester.pumpAndSettle();

        // Some tricks to obtain runtime type for templates
        final foo = DropdownMenuItem<String>(child: Text(""));
        Type dropDownMenuItemString = foo.runtimeType;

        final foo2 = DropdownButton<String>(items: [foo]);
        Type dropDownButton = foo2.runtimeType;

        expect(find.byType(dropDownButton), findsOneWidget);
        expect(find.byType(dropDownButton), findsOneWidget);
        expect(find.byType(dropDownMenuItemString), findsNWidgets(2));
        expect(find.text("table 1"), findsOneWidget);
        expect(find.text("table 2"), findsOneWidget);
  });

  testWidgets("table view",
          (WidgetTester tester) async {
        await tester.pumpWidget(MaterialApp(home: Scaffold(body: Center(child: TableView(mockBackend, "db", "table")))));
        await tester.pumpAndSettle();

        expect(find.byType(DataTable), findsOneWidget);
        expect(find.text("real_attribute"), findsOneWidget);
        expect(find.text("string_attribute"), findsOneWidget);
        expect(find.text("integer_interval_attribute"), findsOneWidget);
  });
}
