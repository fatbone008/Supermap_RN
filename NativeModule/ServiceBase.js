import {NativeModules} from 'react-native';
let SB = NativeModules.JSServiceBase;

export default class ServiceBase {

    static REQUESTFAILED = "com.supermap.services.ServiceBase.requestFailed";
    static REQUESTSUCCESS = "com.supermap.services.ServiceBase.requestSuccess";
    static RECEIVERESPONSE = "com.supermap.services.ServiceBase.receiveResponse";
    static DATASERVICEFINISHED = "com.supermap.services.ServiceBase.dataServiceFinished";

    async getUrl(){
        try{
            var url = await SB.getUrl(this._serviceBaseId_);
            return url;
        }catch(e){
            console.error(e);
        }
    }

    async setUrl(url){
        try{
            await SB.setUrl(this._serviceBaseId_,url);
        }catch(e){
            console.error(e);
        }
    }

    async setServerName(serverName){
        try{
            await SB.setServerName(this._serviceBaseId_,serverName);
        }catch(e){
            console.error(e);
        }
    }

    async setResponseCallback(requestFailed,requestSuccess,receiveResponse,dataServiceFinished){
        try {
            DeviceEventEmitter.addListener(this.REQUESTFAILED,function (e) {
                if(typeof requestFailed == 'function'){
                    requestFailed(e);
                }else{
                    console.error("Could not find the first argument.");
                }
            });
            DeviceEventEmitter.addListener(this.REQUESTSUCCESS,function (e) {
                if(typeof requestSuccess == 'function'){
                    requestSuccess(e);
                }else{
                    console.error("Could not find the second argument.");
                }
            });
            DeviceEventEmitter.addListener(this.RECEIVERESPONSE,function (e) {
                if(typeof receiveResponse == 'function'){
                    receiveResponse(e);
                }else{
                    console.error("Could not find the second argument.");
                }
            });
            DeviceEventEmitter.addListener(this.DATASERVICEFINISHED,function (e) {
                if(typeof dataServiceFinished == 'function'){
                    dataServiceFinished(e);
                }else{
                    console.error("Could not find the second argument.");
                }
            });
        }catch(e){
            console.error(e);
        }

    }
}