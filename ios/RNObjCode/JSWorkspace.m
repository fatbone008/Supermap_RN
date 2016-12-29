//
//  JSWorkspace.m
//  iMobileRnIos
//
//  Created by imobile-xzy on 16/5/12.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSObjManager.h"
#import "JSWorkspace.h"
#import "SuperMap/Workspace.h"
#import "JSWorkspaceConnectionInfo.h"
#import "JSDatasourceConnectionInfo.h"
#import "JSDatasources.h"
#import "JSMaps.h"

@implementation JSWorkspace
@synthesize bridge = _bridge;
//注册为Native模块
RCT_EXPORT_MODULE();



#pragma mark - Public APIs

RCT_REMAP_METHOD(createObj,resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Workspace* workspace = [[Workspace alloc]init];
  NSLog(@"%@",NSHomeDirectory());
  if(workspace){
     NSInteger key = (NSInteger)workspace;
    [JSObjManager addObj:workspace];
    resolve(@{@"workspaceId":@(key).stringValue});
  }else{
    reject(@"WorkSpaceInfo",@"create workSaceInfo failed!!!",nil);
  }
}
RCT_REMAP_METHOD(destroyObj,destroyJSObjKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Workspace* workspace = [JSObjManager getObjWithKey:key];
  if(workspace){
    [workspace close];
    [workspace dispose];
    [JSObjManager removeObj:key];
    resolve(@"1");
  }else
    reject(@"workspace",@"destroy obj failed!!!",nil);
}

RCT_REMAP_METHOD(open, openKey:(NSString*)key infoKey:(NSString*)infoKey resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Workspace* workspace = [JSObjManager getObjWithKey:key];
  WorkspaceConnectionInfo* infoOC = [JSObjManager getObjWithKey:infoKey];
  if(workspace==nil || infoOC==nil){
    reject(@"workspace",@"open failed!!!",nil);
  }else{
    if([workspace open:infoOC]){
      NSLog(@"open workspace succuss!!!");
      
    }else{
       NSLog(@"open workspace failed!!!");
    }
    resolve(@{@"isOpen":@"YES"});
  }
}

RCT_REMAP_METHOD(save,saveKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  
  Workspace* workspace = [JSObjManager getObjWithKey:key];
  if(workspace){
    [workspace save];
    resolve(@"1");
  }else
    reject(@"workspace",@"save failed!!!",nil);
}

RCT_REMAP_METHOD(close,closeKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  
  Workspace* workspace = [JSObjManager getObjWithKey:key];
  if(workspace){
    [workspace close];
    resolve(@"1");
  }else
    reject(@"workspace",@"save failed!!!",nil);
}

RCT_REMAP_METHOD(dispose,disposeKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  
  Workspace* workspace = [JSObjManager getObjWithKey:key];
  if(workspace){
    [workspace dispose];
    resolve(@"1");
  }else
    reject(@"workspace",@"save failed!!!",nil);
}

RCT_REMAP_METHOD(getMaps,geMapsKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Workspace* workspace = [JSObjManager getObjWithKey:key];
  if(workspace){
    Maps* maps = workspace.maps;
    [JSObjManager addObj:maps];
    NSInteger key = (NSInteger)maps;
    resolve(@{@"mapsId":@(key).stringValue});
  }else
    reject(@"mapsId",@"workspace not exeist!!!",nil);
}
RCT_REMAP_METHOD(getDatasources,getDatasourcesKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Workspace* workspace = [JSObjManager getObjWithKey:key];
  if(workspace){
    Datasources* dataSource = workspace.datasources;
    [JSObjManager addObj:dataSource];
    NSInteger key = (NSInteger)dataSource;
    resolve(@{@"datasourcesId":@(key).stringValue});
  }else{
    reject(@"workspace",@"workspace not exeist!!!",nil);
  }
}
@end
