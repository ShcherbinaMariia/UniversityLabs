import 'package:flutter/widgets.dart';

class FilterForm extends StatefulWidget {
  @override
  FilterFormState createState() {
    return FilterFormState();
  }
}

class FilterFormState extends State<FilterForm> {
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Form(
        key: _formKey,
        child: Column(
            children: <Widget>[
              
            ]
        )
    );
  }
}
