/**
 * Created by will on 2017/3/3.
 */
/**
 * 原生Point类点的x，y坐标，转地图坐标Point2D
 * @param x
 * @param y
 * @returns {Promise.<Point2D>}
 * @constructor
 */

import Point from '../Point';

exports.Point2Map = async function (map,x, y) {
    var pointFac = new Point();
    var point = await pointFac.createObj(x,y);
    var mapPoint = await map.pixelToMap(point);

    return mapPoint;
}