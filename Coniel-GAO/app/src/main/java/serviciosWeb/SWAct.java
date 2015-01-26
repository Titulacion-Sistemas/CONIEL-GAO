package serviciosWeb;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import clases.Tupla;

public class SWAct {

    private String namespace = "serviciosWeb.views";
   // private String url="http://192.168.10.60/sw/";
    private String url="http://coniel.servehttp.com:8000/sw/";
    //private String url="http://coniel.servehttp.com/sw/";
    private String soapAction = "";
    SoapObject request = null;

    public SWAct(String url, String methodName){
        this.url+=url;
        this.soapAction=this.namespace+"/"+methodName;
        request = new SoapObject(namespace, methodName);
    }

    public void asignarPropiedades(PropertyInfo p){
            request.addProperty(p);
    }

    public Object ajecutar() {

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
