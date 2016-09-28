import {NativeModules} from 'react-native';
let S = NativeModules.JSSelection;
import Recordset from './Recordset.js';

export default class Selection {
    async fromRecordset(recordset){
        try{
            let {fromRecordset} = await S.fromRecordset(this.selectionId,recordset.recordsetId);
            return fromRecordset;
        }catch(e){
            console.log(e);
        }
    }

    async setStyle(geoStyle){
        try{
            await S.setStyle(this.selectionId,geoStyle.geoStyleId);
        }catch(e){
            console.log(e);
        }
    }

    async clear(){
        try{
            await S.clear(this.selectionId);
        }catch(e){
            console.log(e);
        }
    }

    async toRecordset(){
        try{
            var {recordsetId} = await S.toRecordset(this.selectionId);
            var recordset = new Recordset();
            recordset.recordsetId = recordsetId;
            return recordset;
        }catch(e){
            console.log(e);
        }
    }
}