package messages;

/**
 * Função static Serve fazer parse ao header dos DataPackets.
 * Retorna um array com os campos.
 */
public  class ParserHeader {

        public static String[] parse(String header)
        {
            header = header.replaceAll("[\n|\r]", "");
            String[] fields = null;
            fields = header.split(" ");
            return fields;
        }

}
