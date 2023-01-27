package com.util;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonReader;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.runtime.Settings;
import com.exception.BusinessException;

public final class Json {
    private static final byte[] NULL_VALUE = new byte[]{'n', 'u', 'l', 'l'};
    private static final DslJson<Object> dslJson =
            new DslJson<>(Settings.withRuntime()
                    .unknownNumbers(JsonReader.UnknownNumberParsing.LONG_AND_DOUBLE)
                    .allowArrayFormat(true)
                    .includeServiceLoader()
                    .includeServiceLoader(Json.class.getClassLoader())
            );

    private static final ThreadLocal<JsonWriter> localJsonWriter = ThreadLocal.withInitial(dslJson::newWriter);

    private Json() {
    }

    public static String encodeToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        var jsonWriter = localJsonWriter.get();
        try {
            if (dslJson.serialize(jsonWriter, obj.getClass(), obj)) {
                return jsonWriter.toString();
            }
        } finally {
            jsonWriter.reset();
        }
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, ErrorMessage.CAN_NOT_ENCODE_JSON_OBJECT_OF_TYPE + obj.getClass());
    }
}
