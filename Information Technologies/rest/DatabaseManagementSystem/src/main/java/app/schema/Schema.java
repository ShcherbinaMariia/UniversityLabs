package app.schema;

import app.schema.attributes.Attribute;
import app.util.MutationResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Schema {
    HashMap<String, Attribute> attributes;

    public Schema() {
        this.attributes = new HashMap<>();
    }

    public Schema(List<Attribute> attributeList) {
        this.attributes = new HashMap<>();
        attributeList.forEach(attribute -> this.attributes.put(attribute.name, attribute));
    }

    public MutationResult addAttribute(Attribute attribute) {
        if (attributes.containsKey(attribute.name)) {
            return MutationResult.Fail("Duplicate attribute entry");
        }
        attributes.put(attribute.name, attribute);
        return MutationResult.Success();
    }

    public Collection<Attribute> listAttributes() {
        return attributes.values();
    }

    public MutationResult deleteAttribute(String key) {
        if (!attributes.containsKey(key)) {
            return MutationResult.Fail("No such attribute");
        }
        attributes.remove(key);
        return MutationResult.Success();
    }

    public Attribute getAttribute(String key) {
        return attributes.getOrDefault(key, null);
    }
}
