//
//  JSLocationManager.h
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/22.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"
//#import "RCTEventDispatcher.h"
#import "RCTEventEmitter.h"
#import "SuperMap/LocationManagePlugin.h"
@interface JSLocationManager : RCTEventEmitter<RCTBridgeModule,locationChangedDelegate>
@property GPSData* oldDataCache;
@end
