import 'package:database_management_system/model/attribute_filter.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FormInput extends FormField<AttributeFilter> {
  FormInput(
    _name,
    _operators, {
    AttributeFilter initialValue,
    FormFieldSetter<AttributeFilter> onSaved,
    FormFieldValidator<AttributeFilter> validator,
  }) : super(
            onSaved: onSaved,
            validator: validator,
            initialValue:
                initialValue ?? AttributeFilter(_name, _operators[0], ""),
            builder: (state) {
              return CustomFormFieldState(state, _name, _operators);
            });
}

class CustomFormFieldState extends StatelessWidget {
  final String _name;
  final List<String> _operators;

  final FormFieldState<AttributeFilter> state;

  CustomFormFieldState(this.state, this._name, this._operators);

  @override
  Widget build(BuildContext context) {
    List<DropdownMenuItem<String>> items = List();
    this._operators.forEach((element) =>
        items.add(DropdownMenuItem(value: element, child: Text(element))));

    return Row(
      children: [
        Expanded(
          child: Container(
            child: Text(_name),
            alignment: Alignment.center,
          ),
        ),
        Expanded(
          child: DropdownButtonFormField(
            value: this.state.value.operator,
            items: items,
            onChanged: (newValue) {
              this.state.value.operator = newValue;
            },
          ),
        ),
        Expanded(
          child: TextFormField(
            onSaved: (value) {
              this.state.value.value = value;
            },
          ),
        ),
      ],
    );
  }
}
