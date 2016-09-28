package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not attributable
 * @version 2.0
 */

public class ProjectionType extends com.supermap.data.Enum {
	private ProjectionType(int value, int ugcValue) {
        super(value, ugcValue);
     }

    public static final ProjectionType PRJ_NONPROJECTION = new ProjectionType(
            43000, 43000); /* 非投影--*/
    public static final ProjectionType PRJ_PLATE_CARREE = new ProjectionType(
            43001, 43001); /* Plate Carree                */
    public static final ProjectionType PRJ_EQUIDISTANT_CYLINDRICAL = new
            ProjectionType(43002, 43002); /* Equidistant Cylindrical      */
    public static final ProjectionType PRJ_MILLER_CYLINDRICAL = new
            ProjectionType(43003, 43003); /* Miller Cylindrical           */
    public static final ProjectionType PRJ_MERCATOR = new ProjectionType(43004,
            43004); /* Mercator                     */
    public static final ProjectionType PRJ_GAUSS_KRUGER = new ProjectionType(
            43005, 43005); /* Gauss-Kruger                 */
    public static final ProjectionType PRJ_TRANSVERSE_MERCATOR = new
            ProjectionType(43006, 43006); /* Transverse Mercator ==43005  */
    public static final ProjectionType PRJ_ALBERS = new ProjectionType(43007,
            43007); /* Albers                       */
    public static final ProjectionType PRJ_SINUSOIDAL = new ProjectionType(
            43008, 43008); /* Sinusoidal                   */
    public static final ProjectionType PRJ_MOLLWEIDE = new ProjectionType(43009,
            43009); /* Mollweide                    */
    public static final ProjectionType PRJ_ECKERT_VI = new ProjectionType(43010,
            43010); /* Eckert VI                    */
    public static final ProjectionType PRJ_ECKERT_V = new ProjectionType(43011,
            43011); /* Eckert V                     */
    public static final ProjectionType PRJ_ECKERT_IV = new ProjectionType(43012,
            43012); /* Eckert IV                    */
    public static final ProjectionType PRJ_ECKERT_III = new ProjectionType(
            43013, 43013); /* Eckert III                   */
    public static final ProjectionType PRJ_ECKERT_II = new ProjectionType(43014,
            43014); /* Eckert II                    */
    public static final ProjectionType PRJ_ECKERT_I = new ProjectionType(43015,
            43015); /* Eckert I                     */
    public static final ProjectionType PRJ_GALL_STEREOGRAPHIC = new
            ProjectionType(43016, 43016); /* Gall Stereographic           */
    public static final ProjectionType PRJ_BEHRMANN = new ProjectionType(43017,
            43017); /* Behrmann                     */
    public static final ProjectionType PRJ_WINKEL_I = new ProjectionType(43018,
            43018); /* Winkel I                     */
    public static final ProjectionType PRJ_WINKEL_II = new ProjectionType(43019,
            43019); /* Winkel II                    */
    public static final ProjectionType PRJ_LAMBERT_CONFORMAL_CONIC = new
            ProjectionType(43020, 43020); /* Lambert Conformal Conic      */
    public static final ProjectionType PRJ_POLYCONIC = new ProjectionType(43021,
            43021); /* Polyconic                    */
    public static final ProjectionType PRJ_QUARTIC_AUTHALIC = new
            ProjectionType(43022, 43022); /* Quartic Authalic             */
    public static final ProjectionType PRJ_LOXIMUTHAL = new ProjectionType(
            43023, 43023); /* Loximuthal                   */
    public static final ProjectionType PRJ_BONNE = new ProjectionType(43024,
            43024); /* Bonne                        */
    public static final ProjectionType PRJ_HOTINE = new ProjectionType(43025,
            43025); /* Hotine                       */
    public static final ProjectionType PRJ_STEREOGRAPHIC = new ProjectionType(
            43026, 43026); /* Stereographic                */
    public static final ProjectionType PRJ_EQUIDISTANT_CONIC = new
            ProjectionType(43027, 43027); /* Equidistant Conic            */
    public static final ProjectionType PRJ_CASSINI = new ProjectionType(43028,
            43028); /* Cassini                      */
    public static final ProjectionType PRJ_VAN_DER_GRINTEN_I = new
            ProjectionType(43029, 43029); /* Van der Grinten I            */
    public static final ProjectionType PRJ_ROBINSON = new ProjectionType(43030,
            43030); /* Robinson                     */
    public static final ProjectionType PRJ_TWO_POINT_EQUIDISTANT = new
            ProjectionType(43031, 43031); /* Two-Point Equidistant        */
    public static final ProjectionType PRJ_EQUIDISTANT_AZIMUTHAL = new
            ProjectionType(43032, 43032); /* Equidistant Azimuthal        */
    public static final ProjectionType PRJ_LAMBERT_AZIMUTHAL_EQUAL_AREA = new
            ProjectionType(43033, 43033); /* Lambert Azimuthal Equal Area*/
    public static final ProjectionType PRJ_CONFORMAL_AZIMUTHAL = new
            ProjectionType(43034, 43034); /* Conformal Azimuthal*/
    public static final ProjectionType PRJ_ORTHO_GRAPHIC = new ProjectionType(
            43035, 43035); /* ortho_graphic 正射**/
    public static final ProjectionType PRJ_GNOMONIC = new ProjectionType(43036,
            43036); /* Gnomonic 球心*/
    public static final ProjectionType PRJ_CHINA_AZIMUTHAL = new ProjectionType(
            43037, 43037); /* 中国全图方位投影*/
    public static final ProjectionType PRJ_SANSON = new ProjectionType(43040,
            43040); /*桑逊投影——正弦曲线等积伪圆柱投影*/
    public static final ProjectionType PRJ_EQUALAREA_CYLINDRICAL = new
            ProjectionType(43041, 43041); /* EqualArea Cylindrical        */
    public static final ProjectionType PRJ_HOTINE_AZIMUTH_NATORIGIN = new
            ProjectionType(43042, 43042);
    
//  以下为6.0新增
    public static final ProjectionType PRJ_OBLIQUE_MERCATOR = new
    ProjectionType(43043, 43043);   // 斜轴墨卡托投影
    
    public static final ProjectionType PRJ_HOTINE_OBLIQUE_MERCATOR = new
    ProjectionType(43044, 43044);   // Hotine斜轴墨卡托投影
    
    public static final ProjectionType PRJ_SPHERE_MERCATOR = new
    ProjectionType(43045, 43045);   // 正球墨卡托
    
    public static final ProjectionType PRJ_BONNE_SOUTH_ORIENTATED = new ProjectionType(43046, 43046); //南半球彭纳投影
   
    public static final ProjectionType PRJ_OBLIQUE_STEREOGRAPHIC = new ProjectionType(43047, 43047); //Oblique stereographic , Esri 称为 double stereographic 斜轴赤平投影
  }
