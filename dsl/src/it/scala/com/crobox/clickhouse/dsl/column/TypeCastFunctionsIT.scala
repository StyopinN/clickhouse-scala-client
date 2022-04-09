package com.crobox.clickhouse.dsl.column

import com.crobox.clickhouse.DslITSpec
import com.crobox.clickhouse.dsl._

class TypeCastFunctionsIT extends DslITSpec {

  it should "handle UInt8" in {
    r(toUInt8("1")) should be("1")
    r(toUInt8(1)) should be("1")

    r(toUInt8(Byte.MaxValue.toString)) should be("127")
    r(toUInt8(Byte.MaxValue)) should be("127")

    r(toUInt8(Short.MaxValue.toString)) should be("255")

    r(toUInt8(Int.MaxValue.toString)) should be("255")
    r(toUInt8(Int.MaxValue)) should be("255")

    r(toUInt8(Long.MaxValue.toString)) should be("255")
    r(toUInt8(Long.MaxValue)) should be("255")

    an[Exception] should be thrownBy r(toUInt8(Float.MaxValue.toString))
    r(toUInt8(Float.MaxValue)) should be("0")

    an[Exception] should be thrownBy r(toUInt8(Double.MaxValue.toString))
    r(toUInt8(Double.MaxValue)) should be("0")
  }

  it should "handle UInt32" in {
    // Unsigned can't handle NEGATIVE numbers
    r(toUInt32(Byte.MaxValue.toString)) should be("127")
    r(toUInt32(Byte.MaxValue)) should be("127")
    an[Exception] should be thrownBy r(toUInt32(Byte.MinValue.toString))
    r(toUInt32(Byte.MinValue)) should be("4294967168")

    r(toUInt32(Short.MaxValue.toString)) should be("32767")
    an[Exception] should be thrownBy r(toUInt32(Short.MinValue.toString))

    r(toUInt32(Int.MaxValue.toString)) should be("2147483647")
    r(toUInt32(Int.MaxValue)) should be("2147483647")
    an[Exception] should be thrownBy r(toUInt32(Int.MinValue.toString))
    r(toUInt32(Int.MinValue)) should be("2147483648")

    r(toUInt32(Long.MaxValue.toString)) should be("4294967295")
    r(toUInt32(Long.MaxValue)) should be("4294967295")
    an[Exception] should be thrownBy r(toUInt32(Long.MinValue.toString))
    r(toUInt32(Long.MinValue)) should be("0")

    an[Exception] should be thrownBy r(toUInt32(Float.MaxValue.toString))
    r(toUInt32(Float.MaxValue)) should be("0")
    an[Exception] should be thrownBy r(toUInt32(Float.MinValue.toString))
    r(toUInt32(Float.MinValue)) should be("0")

    an[Exception] should be thrownBy r(toUInt32(Double.MaxValue.toString))
    r(toUInt32(Double.MaxValue)) should be("0")
    an[Exception] should be thrownBy r(toUInt32(Double.MinValue.toString))
    r(toUInt32(Double.MinValue)) should be("0")
  }

  // this is the java/scala int (since we need 1 byte for negative)
  it should "handle Int32" in {
    r(toInt32(Byte.MaxValue.toString)) should be("127")
    r(toInt32(Byte.MaxValue)) should be("127")
    r(toInt32(Byte.MinValue.toString)) should be("-128")
    r(toInt32(Byte.MinValue)) should be("-128")

    r(toInt32(Short.MaxValue.toString)) should be("32767")
    r(toInt32(Short.MinValue.toString)) should be("-32768")

    r(toInt32(Int.MaxValue.toString)) should be("2147483647")
    r(toInt32(Int.MaxValue)) should be("2147483647")
    r(toInt32(Int.MinValue.toString)) should be("-2147483648")
    r(toInt32(Int.MinValue)) should be("-2147483648")

    r(toInt32(Long.MaxValue.toString)) should be("-1")
    r(toInt32(Long.MaxValue)) should be("-1")
    r(toInt32(Long.MinValue.toString)) should be("0")
    r(toInt32(Long.MinValue)) should be("0")

    an[Exception] should be thrownBy r(toInt32(Float.MaxValue.toString))
    r(toInt32(Float.MaxValue)) should be("-2147483648")
    an[Exception] should be thrownBy r(toInt32(Float.MinValue.toString))
    r(toInt32(Float.MinValue)) should be("-2147483648")

    an[Exception] should be thrownBy r(toInt32(Double.MaxValue.toString))
    r(toInt32(Double.MaxValue)) should be("-2147483648")
    an[Exception] should be thrownBy r(toInt32(Double.MinValue.toString))
    r(toInt32(Double.MinValue)) should be("-2147483648")
  }

  it should "handle Int32OrZero" in {
    // orZero only accept STRING
    r(toInt32OrZero("1")) should be("1")
    r(toInt32OrZero(Byte.MaxValue.toString)) should be("127")
    r(toInt32OrZero(Byte.MinValue.toString)) should be("-128")
    r(toInt32OrZero(Short.MaxValue.toString)) should be("32767")
    r(toInt32OrZero(Short.MinValue.toString)) should be("-32768")
    r(toInt32OrZero(Int.MaxValue.toString)) should be("2147483647")
    r(toInt32OrZero(Int.MinValue.toString)) should be("-2147483648")
    r(toInt32OrZero(Long.MaxValue.toString)) should be("0")
    r(toInt32OrZero(Long.MinValue.toString)) should be("0")
    r(toInt32OrZero(Float.MaxValue.toString)) should be("0")
    r(toInt32OrZero(Float.MinValue.toString)) should be("0")
    r(toInt32OrZero(Double.MaxValue.toString)) should be("0")
    r(toInt32OrZero(Double.MinValue.toString)) should be("0")
  }

  def r(col: TypeCastColumn[_]): String =
    execute(select(col)).futureValue
}
