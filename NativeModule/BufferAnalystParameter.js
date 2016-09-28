import {NativeModules} from 'react-native';
let BAP = NativeModules.JSBufferAnalystParameter;

export default class BufferAnalystParameter {
    static ENDTYPE = {
        ROUND:1,
        FLAT:2,
    }

    async createObj(){
        try{
            var {bufferAnalystParameterId} = await BAP.createObj();
            var bufferAnalystParameter = new BufferAnalystParameter();
            bufferAnalystParameter.bufferAnalystParameterId = bufferAnalystParameterId;
            return bufferAnalystParameter;
        }catch (e){
            console.error(e);
        }
    }

    async setEndType(bufferEndType){
        try{
            await BAP.setEndType(this.bufferAnalystParameterId,bufferEndType);
        }catch (e){
            console.error(e);
        }
    }

    async setLeftDistance(distance){
        try{
            await BAP.setLeftDistance(this.bufferAnalystParameterId,distance);
        }catch (e){
            console.error(e);
        }
    }

    async setRightDistance(distance){
        try{
            await BAP.setRightDistance(this.bufferAnalystParameterId,distance);
        }catch (e){
            console.error(e);
        }
    }
}