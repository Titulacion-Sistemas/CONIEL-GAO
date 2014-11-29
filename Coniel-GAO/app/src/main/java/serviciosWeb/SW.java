package serviciosWeb;

import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import clases.Tupla;

public class SW {

    private String namespace = "serviciosWeb.views";
    private String url="http://192.168.10.60/sw/";
    //private String url="http://coniel.servehttp.com:8000/sw/";
    //private String url="http://coniel.servehttp.com/sw/";
    private String soapAction = "";
    SoapObject request = null;

    public SW(String url, String methodName ){
        this.url+=url;
        this.soapAction=this.namespace+"/"+methodName;
        request = new SoapObject(namespace, methodName);
    }

    public void asignarPropiedades(Tupla<String, Object>[] propiedades){
        for (Tupla<String, Object> p : propiedades){
            request.addProperty(p.getKey(),p.getValue());
        }
    }

    public Object ajecutar() {

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(url);
        Object resSoap;

        try
        {
            transporte.call(soapAction, envelope);
            resSoap = envelope.getResponse();
            System.out.println("valor asignado a la respuesta");
            return resSoap;
        }
        catch (Exception e)
        {
            Log.e("retorno falso", e.toString());
        }

        return null;
    }

}
