import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FormInput extends StatefulWidget{
  FormInput(this._name, this._operators);
  final String _name;
  final List<String> _operators;

  @override
  State<StatefulWidget> createState() => FormInputState();
}

class FormInputState extends State<FormInput> {
  @override
  Widget build(BuildContext context) {
    return Row(children:[
      Text(widget._name),
      DropdownButtonFormField(items: widget._operators.map((element) => DropdownMenuItem(child: Text(element))), onChanged: null),
      TextFormField(),
    ],);
  }
}