//
//  JSMapView.h
//  rnTest
//
//  Created by imobile-xzy on 16/7/12.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RCTBridgeModule.h"
#import "SuperMap/MapControl.h"
@interface JSMapView  : NSObject<RCTBridgeModule,MapMeasureDelegate,GeometrySelectedDelegate,MapEditDelegate,TouchableViewDelegate>

@end
