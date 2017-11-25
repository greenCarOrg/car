/**
 *
 */
package com.itee.bingsheng.orm;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * @author Aidy_He
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    /**
     *
     */
    private static final long serialVersionUID = -2320897294374432293L;

    /**
     *
     */
    public HibernateAwareObjectMapper() {
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Hibernate4Module module = new Hibernate4Module();
        module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        registerModule(module);
    }


    /**
     * @param jf
     */
    public HibernateAwareObjectMapper(JsonFactory jf) {
        super(jf);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param src
     */
    public HibernateAwareObjectMapper(ObjectMapper src) {
        super(src);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param jf
     * @param sp
     * @param dc
     */
    public HibernateAwareObjectMapper(JsonFactory jf,
									  DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
        super(jf, sp, dc);
        // TODO Auto-generated constructor stub
    }

}
