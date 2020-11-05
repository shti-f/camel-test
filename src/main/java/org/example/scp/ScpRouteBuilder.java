package org.example.scp;

import org.apache.camel.builder.RouteBuilder;

// ローカルのファイルをリモートにコピーする
// camel-jschをpom.xmlに追加する必要あり
public class ScpRouteBuilder extends RouteBuilder {

    String user = "root";
    String ip = "127.0.0.1:20019"; // リモートのホスト名と、（あれば）ポート番号
    String destination = "~/"; // リモートで、コピーしたい先のパス
    String copiedFileName = ""; // ""の場合はファイル名そのままコピーされる。指定した場合はそのファイル名としてコピーされる。
    String uri = String.format("%s@%s/%s%s", user, ip, destination, copiedFileName);

    String privateKeyFileUri = "src/main/resources/id_rsa_pem"; // 秘密鍵（PEM形式）のURI
    String parameter = String.format("?privateKeyFile=%s&strictHostKeyChecking=no", privateKeyFileUri);

    public void configure(){

        System.out.println("scp:" + uri + parameter);

        // fromで指定したファイルがリモートにコピーされる
        from("file:src/data?noop=true&fileName=message1.xml").routeId("scpRoute")
                .log("body -> ${body}")
                .to("scp:" + uri + parameter)
                .log("done");
    }
}
