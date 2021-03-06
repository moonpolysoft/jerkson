package com.codahale.jerkson.ser

import org.codehaus.jackson.`type`.JavaType
import com.codahale.jerkson.AST.JValue
import org.codehaus.jackson.map._

/**
 *
 * @author coda
 */
class ScalaSerializers extends Serializers {
  def findSerializer(config: SerializationConfig, javaType: JavaType, beanDesc: BeanDescription, beanProp: BeanProperty) = {
    val ser = if (classOf[Seq[_]].isAssignableFrom(beanDesc.getBeanClass)) {
      new SeqSerializer
    } else if (classOf[Map[_,_]].isAssignableFrom(beanDesc.getBeanClass)) {
      new MapSerializer
    } else if (classOf[JValue].isAssignableFrom(beanDesc.getBeanClass)) {
      new JValueSerializer
    } else if (classOf[Either[_,_]].isAssignableFrom(beanDesc.getBeanClass)) {
      new EitherSerializer
    } else if (classOf[Product].isAssignableFrom(beanDesc.getBeanClass)) {
      new CaseClassSerializer(beanDesc.getBeanClass)
    } else {
      null
    }
    ser.asInstanceOf[JsonSerializer[Object]]
  }
}
