import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class BuildSchema {

    public static void main(String[] args) {
        Schema person = SchemaBuilder.record("Person") //
                                     .namespace("id.gridgo.bean.serialization.binary.avro.test.support") //
                                     .fields() //
                                     .requiredString("name") //
                                     .requiredInt("age") //
                                     .endRecord();
        System.out.println(person.toString());
    }
}
