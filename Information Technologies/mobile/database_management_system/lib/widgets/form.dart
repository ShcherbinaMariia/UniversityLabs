import 'package:database_management_system/model/attribute_filter.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'form_input.dart';

class FilterFormScreen extends StatelessWidget {
  final Map<String, List<String>> _availableFilters;
  final Function _onFormSubmit;

  FilterFormScreen(this._availableFilters, this._onFormSubmit);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text("Filter")),
        body: FilterForm(this._availableFilters, this._onFormSubmit));
  }
}

class FilterForm extends StatefulWidget {
  FilterForm(this._availableFilters, this._onFormSubmit);

  final Map<String, List<String>> _availableFilters;
  final Function _onFormSubmit;

  @override
  State<StatefulWidget> createState() => FilterFormState();
}

class FilterFormState extends State<FilterForm> {
  final _formKey = GlobalKey<FormState>();
  List<AttributeFilter> inputs = List();

  void submit() {
    inputs = List();
    _formKey.currentState.save();
    widget._onFormSubmit(inputs);
    Navigator.pop(context);
  }

  void save(AttributeFilter attributeFilter) {
    inputs.add(attributeFilter);
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> items = List();
    widget._availableFilters.forEach((name, operands) {
      items.add(FormInput(name, operands, onSaved: save));
    });

    items.add(ElevatedButton(onPressed: submit, child: Text("Submit")));

    return Form(
        key: _formKey,
        child: Column(
          children: items,
        ));
  }
}
