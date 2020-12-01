import 'package:sprintf/sprintf.dart';

const OPERATOR_SUFFIX = "_op";
const VALUE_SUFFIX = "_val";

class AttributeFilter {
  String _name;
  String _operator;
  String _value;

  AttributeFilter(this._name, this._operator, this._value);

  String toParamString() {
    return sprintf("%s%s=%s&%s%s=%s", [this._name, OPERATOR_SUFFIX, this._operator, this._name, VALUE_SUFFIX, this._value]);
  }
}