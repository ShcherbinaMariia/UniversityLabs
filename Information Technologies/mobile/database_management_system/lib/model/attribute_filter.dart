import 'package:sprintf/sprintf.dart';

const OPERATOR_SUFFIX = "_op";
const VALUE_SUFFIX = "_val";

class AttributeFilter {
  String name;
  String operator;
  String value;

  AttributeFilter(this.name, this.operator, this.value);

  String toParamString() {
    if (this.value == "") {
      return "";
    }
    return sprintf("%s%s=%s&%s%s=%s", [this.name, OPERATOR_SUFFIX, this.operator, this.name, VALUE_SUFFIX, this.value]);
  }
}