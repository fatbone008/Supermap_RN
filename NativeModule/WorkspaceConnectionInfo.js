/**
 * Created by will on 2016/5/17.
 */
var {NativeModules}=require('react-native');
let WCI=NativeModules.JSWorkspaceConnectionInfo;

export default class WorkspaceConnectionInfo{
    async createJSObj(){
        try{
            var {ID}=await WCI.createJSObj();
            var workspaceConnectionInfo = new WorkspaceConnectionInfo();
            workspaceConnectionInfo.workspaceConnectionInfoId = ID;
            return workspaceConnectionInfo;
        }catch(e){
            console.error(e);
        }
    }

    async setType(type){
        try{
            await WCI.setType(this.workspaceConnectionInfoId,type);
        }catch(e){
            console.error(e);
        }
    }

    async setServer(path){
        try{
            await WCI.setServer(this.workspaceConnectionInfoId,path);
        }catch(e){
            console.error(e);
        }
    }

}