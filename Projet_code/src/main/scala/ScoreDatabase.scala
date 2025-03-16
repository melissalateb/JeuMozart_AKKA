package org.example.mozartgame

import akka.actor._

object ScoreDatabase {
  // Définitions des éléments musicaux
  abstract class MusicElement
  case class Tone(pitch: Int, duration: Int, volume: Int) extends MusicElement
  case class Chord(startTime: Int, tones: List[Tone]) extends MusicElement
  case class Bar(chords: List[Chord]) extends MusicElement
  
  // Messages pour la récupération d'un bar
  case class GetBar(num: Int)
  case class Begin()
  
  // Première partie de la base de données (bars1)
  val bars1: List[Bar] = List(
    Bar(List(
      Chord(0, List(Tone(42,610,86), Tone(54,594,81), Tone(81,315,96))),
      Chord(292, List(Tone(78,370,78))),
      Chord(601, List(Tone(76,300,91), Tone(43,585,83), Tone(55,588,98))),
      Chord(910, List(Tone(79,335,96))),
      Chord(1189, List(Tone(73,342,86), Tone(57,595,76), Tone(45,607,83))),
      Chord(1509, List(Tone(76,280,93)))
    )),
    Bar(List(
      Chord(0, List(Tone(49,616,86))),
      Chord(295, List(Tone(64,314,78))),
      Chord(583, List(Tone(52,616,88), Tone(68,296,81))),
      Chord(863, List(Tone(69,290,96))),
      Chord(1168, List(Tone(57,607,79), Tone(73,305,79))),
      Chord(1473, List(Tone(76,310,100)))
    )),
    Bar(List(
      Chord(0, List(Tone(50,596,91), Tone(66,575,108), Tone(74,588,108))),
      Chord(612, List(Tone(54,297,76), Tone(66,576,85), Tone(74,576,85))),
      Chord(901, List(Tone(57,305,78))),
      Chord(1181, List(Tone(66,575,69), Tone(74,575,69), Tone(62,610,86)))
    )),
    Bar(List(
      Chord(0, List(Tone(76,447,105), Tone(43,636,86), Tone(55,616,85))),
      Chord(435, List(Tone(71,150,69))),
      Chord(595, List(Tone(42,600,108), Tone(54,600,108), Tone(69,300,108))),
      Chord(995, List(Tone(71,150,69))),
      Chord(1197, List(Tone(67,538,108), Tone(40,567,93), Tone(52,588,102)))
    )),
    Bar(List(
      Chord(0, List(Tone(45,374,96), Tone(69,1787,108))),
      Chord(143, List(Tone(49,360,86))),
      Chord(287, List(Tone(52,317,88))),
      Chord(432, List(Tone(57,340,85))),
      Chord(578, List(Tone(45,628,86)))
    )),
    Bar(List(
      Chord(0, List(Tone(79,302,96), Tone(59,608,91), Tone(47,627,103))),
      Chord(275, List(Tone(74,311,88))),
      Chord(591, List(Tone(43,585,107), Tone(55,600,102), Tone(83,278,85))),
      Chord(884, List(Tone(79,317,83))),
      Chord(1195, List(Tone(81,545,95), Tone(42,587,91), Tone(54,590,96)))
    )),
    Bar(List(
      Chord(0, List(Tone(70,289,108))),
      Chord(284, List(Tone(69,300,83))),
      Chord(597, List(Tone(69,883,105), Tone(61,600,108), Tone(57,600,108))),
      Chord(1197, List(Tone(61,593,119), Tone(57,581,119))),
      Chord(1492, List(Tone(81,287,108)))
    )),
    Bar(List(
      Chord(0, List(Tone(74,1762,108))),
      Chord(277, List(Tone(50,300,108))),
      Chord(577, List(Tone(45,300,108))),
      Chord(876, List(Tone(42,300,108))),
      Chord(1157, List(Tone(38,600,108)))
    )),
    Bar(List(
      Chord(0, List(Tone(81,300,108), Tone(54,300,108))),
      Chord(273, List(Tone(66,300,108), Tone(69,300,74))),
      Chord(563, List(Tone(52,300,108), Tone(79,300,108))),
      Chord(877, List(Tone(67,300,68), Tone(64,300,108))),
      Chord(1186, List(Tone(78,562,91), Tone(50,300,108))),
      Chord(1476, List(Tone(62,300,108)))
    )),
    Bar(List(
      Chord(0, List(Tone(67,600,108), Tone(40,600,108), Tone(52,600,108))),
      Chord(582, List(Tone(79,300,108), Tone(37,600,108), Tone(49,600,108))),
      Chord(876, List(Tone(76,300,108))),
      Chord(1185, List(Tone(78,300,108), Tone(38,600,108), Tone(50,600,108))),
      Chord(1505, List(Tone(76,150,79))),
      Chord(1628, List(Tone(74,150,74)))
    )),
    Bar(List(
      Chord(0, List(Tone(78,300,108), Tone(62,600,108))),
      Chord(298, List(Tone(76,150,79))),
      Chord(422, List(Tone(74,150,74))),
      Chord(579, List(Tone(73,600,108), Tone(64,600,108), Tone(69,600,108))),
      Chord(1185, List(Tone(68,570,108), Tone(71,580,108), Tone(52,600,108)))
    )),
    Bar(List(
      Chord(0, List(Tone(83,600,108), Tone(43,600,108))),
      Chord(582, List(Tone(83,150,108), Tone(55,600,108))),
      Chord(731, List(Tone(81,167,108))),
      Chord(892, List(Tone(79,150,108))),
      Chord(1033, List(Tone(78,150,108))),
      Chord(1195, List(Tone(76,150,108))),
      Chord(1337, List(Tone(74,150,108))),
      Chord(1500, List(Tone(73,150,108))),
      Chord(1636, List(Tone(71,150,108)))
    )),
    Bar(List(
      Chord(0, List(Tone(81,878,100), Tone(78,882,90))),
      Chord(615, List(Tone(57,607,103))),
      Chord(918, List(Tone(79,245,83), Tone(76,254,85))),
      Chord(1205, List(Tone(74,558,102), Tone(78,555,95), Tone(62,582,88)))
    )),
    Bar(List(
      Chord(0, List(Tone(74,1743,98), Tone(50,620,95))),
      Chord(594, List(Tone(45,613,98))),
      Chord(1189, List(Tone(38,587,90)))
    )),
    Bar(List(
      Chord(0, List(Tone(69,140,102))),
      Chord(135, List(Tone(71,162,93))),
      Chord(304, List(Tone(73,143,86))),
      Chord(447, List(Tone(71,142,91))),
      Chord(604, List(Tone(55,530,98), Tone(64,527,98), Tone(61,540,76), Tone(69,252,100))),
      Chord(888, List(Tone(81,600,90))),
      Chord(1180, List(Tone(64,557,79), Tone(55,592,79), Tone(61,570,79))),
      Chord(1484, List(Tone(69,293,102)))
    )),
    Bar(List(
      Chord(0, List(Tone(51,199,103), Tone(71,1146,98))),
      Chord(181, List(Tone(54,219,96))),
      Chord(365, List(Tone(59,252,88))),
      Chord(592, List(Tone(51,206,91))),
      Chord(792, List(Tone(54,206,83))),
      Chord(991, List(Tone(59,215,74))),
      Chord(1190, List(Tone(51,211,83), Tone(83,146,96))),
      Chord(1339, List(Tone(54,207,76), Tone(81,164,85))),
      Chord(1473, List(Tone(79,189,86))),
      Chord(1585, List(Tone(78,150,85), Tone(59,187,68)))
    )),
    Bar(List(
      Chord(0, List(Tone(75,150,108))),
      Chord(143, List(Tone(76,450,108))),
      Chord(598, List(Tone(81,150,108), Tone(55,577,96), Tone(57,1177,90))),
      Chord(753, List(Tone(73,450,108))),
      Chord(1200, List(Tone(54,555,93), Tone(74,150,108))),
      Chord(1349, List(Tone(69,437,108)))
    )),
    Bar(List(
      Chord(0, List(Tone(81,185,117))),
      Chord(157, List(Tone(78,189,96))),
      Chord(314, List(Tone(73,112,91))),
      Chord(456, List(Tone(74,180,76))),
      Chord(631, List(Tone(54,183,76), Tone(69,600,96))),
      Chord(997, List(Tone(62,188,79))),
      Chord(1187, List(Tone(54,219,73))),
      Chord(1375, List(Tone(57,239,78))),
      Chord(1601, List(Tone(62,184,86)))
    )),
    Bar(List(
      Chord(0, List(Tone(73,365,90))),
      Chord(301, List(Tone(76,294,91))),
      Chord(605, List(Tone(55,535,85), Tone(57,550,71))),
      Chord(896, List(Tone(76,275,95))),
      Chord(1199, List(Tone(55,537,96), Tone(57,547,90), Tone(74,147,100))),
      Chord(1326, List(Tone(73,169,64))),
      Chord(1495, List(Tone(71,160,90))),
      Chord(1638, List(Tone(69,137,90)))
    )),
    Bar(List(
      Chord(0, List(Tone(76,332,98))),
      Chord(318, List(Tone(78,286,88))),
      Chord(599, List(Tone(52,313,91), Tone(79,306,102))),
      Chord(893, List(Tone(81,285,74), Tone(54,287,78))),
      Chord(1203, List(Tone(55,535,93), Tone(83,575,107)))
    )),
    Bar(List(
      Chord(0, List(Tone(71,281,108), Tone(50,616,88))),
      Chord(294, List(Tone(74,255,119), Tone(78,277,119))),
      Chord(566, List(Tone(52,558,107), Tone(73,296,120), Tone(76,292,100))),
      Chord(873, List(Tone(74,290,107), Tone(71,300,107))),
      Chord(1165, List(Tone(40,570,98), Tone(69,300,91), Tone(73,338,91))),
      Chord(1466, List(Tone(71,307,108), Tone(68,300,100)))
    )),
    Bar(List(
      Chord(0, List(Tone(45,556,105), Tone(52,553,112), Tone(79,150,112))),
      Chord(145, List(Tone(81,158,96))),
      Chord(306, List(Tone(83,156,85))),
      Chord(449, List(Tone(79,126,90))),
      Chord(583, List(Tone(76,152,98), Tone(49,583,105), Tone(61,582,108))),
      Chord(753, List(Tone(78,143,78))),
      Chord(899, List(Tone(79,131,95))),
      Chord(1028, List(Tone(76,170,76))),
      Chord(1178, List(Tone(62,550,100), Tone(50,567,98), Tone(78,600,93)))
    )),
    Bar(List(
      Chord(0, List(Tone(71,161,102), Tone(55,590,98))),
      Chord(146, List(Tone(83,175,96))),
      Chord(289, List(Tone(81,150,83))),
      Chord(426, List(Tone(79,183,93))),
      Chord(576, List(Tone(74,600,93), Tone(78,600,100), Tone(57,620,100))),
      Chord(1195, List(Tone(45,593,96), Tone(76,581,93), Tone(73,581,90)))
    )),
    Bar(List(
      Chord(0, List(Tone(57,626,90), Tone(69,1762,96))),
      Chord(592, List(Tone(52,453,108))),
      Chord(1032, List(Tone(49,170,96))),
      Chord(1185, List(Tone(45,557,91)))
    )),
    Bar(List(
      Chord(0, List(Tone(42,559,79), Tone(54,574,83), Tone(69,629,102))),
      Chord(601, List(Tone(81,174,107))),
      Chord(742, List(Tone(79,188,74))),
      Chord(888, List(Tone(78,184,66))),
      Chord(1050, List(Tone(76,177,79))),
      Chord(1181, List(Tone(75,584,78), Tone(47,555,86), Tone(59,533,86)))
    )),
    Bar(List(
      Chord(0, List(Tone(79,442,110))),
      Chord(425, List(Tone(83,167,88))),
      Chord(577, List(Tone(55,552,83), Tone(43,520,83), Tone(76,465,93))),
      Chord(1047, List(Tone(79,165,78))),
      Chord(1190, List(Tone(45,529,71), Tone(73,449,86), Tone(57,514,76))),
      Chord(1622, List(Tone(76,153,73)))
    ))
  )
  
  // Deuxième partie de la base de données (bars2)
  val bars2: List[Bar] = List(
    Bar(List(
      Chord(0, List(Tone(57,614,105), Tone(69,612,91))),
      Chord(605, List(Tone(55,293,96), Tone(67,285,86))),
      Chord(898, List(Tone(66,282,78), Tone(54,294,93))),
      Chord(1198, List(Tone(52,300,88), Tone(64,302,81))),
      Chord(1495, List(Tone(50,280,85), Tone(62,297,90)))
    )),
    Bar(List(
      Chord(0, List(Tone(68,437,95))),
      Chord(440, List(Tone(71,155,91))),
      Chord(602, List(Tone(59,547,91), Tone(50,550,91), Tone(56,548,90), Tone(76,447,96), Tone(76,200,96))),
      Chord(1043, List(Tone(71,152,93))),
      Chord(1191, List(Tone(56,565,90), Tone(80,443,96), Tone(59,567,88), Tone(50,583,88))),
      Chord(1643, List(Tone(71,150,98)))
    )),
    Bar(List(
      Chord(0, List(Tone(55,1777,100))),
      Chord(306, List(Tone(71,305,78))),
      Chord(595, List(Tone(75,298,81), Tone(59,592,81))),
      Chord(906, List(Tone(76,288,86))),
      Chord(1195, List(Tone(79,310,90), Tone(64,575,90))),
      Chord(1511, List(Tone(83,276,93)))
    )),
    Bar(List(
      Chord(0, List(Tone(69,300,96))),
      Chord(305, List(Tone(81,280,110))),
      Chord(605, List(Tone(60,556,81), Tone(57,549,81), Tone(79,289,105))),
      Chord(899, List(Tone(78,295,96))),
      Chord(1202, List(Tone(60,555,90), Tone(76,290,91), Tone(57,555,88))),
      Chord(1499, List(Tone(75,290,98)))
    )),
    Bar(List(
      Chord(0, List(Tone(78,152,88))),
      Chord(75, List(Tone(76,152,79))),
      Chord(149, List(Tone(75,152,102))),
      Chord(224, List(Tone(76,150,78))),
      Chord(302, List(Tone(71,1171,103), Tone(55,214,91))),
      Chord(400, List(Tone(59,195,61))),
      Chord(501, List(Tone(64,200,68))),
      Chord(595, List(Tone(55,210,90))),
      Chord(701, List(Tone(59,196,66))),
      Chord(800, List(Tone(64,198,74)))
    )),
    Bar(List(
      Chord(0, List(Tone(74,1173,102), Tone(56,297,86))),
      Chord(297, List(Tone(64,280,78))),
      Chord(597, List(Tone(59,300,85))),
      Chord(893, List(Tone(64,314,73))),
      Chord(1195, List(Tone(56,300,86), Tone(83,318,96))),
      Chord(1487, List(Tone(64,293,79), Tone(74,300,103)))
    )),
    Bar(List(
      Chord(0, List(Tone(66,197,93))),
      Chord(197, List(Tone(69,195,91))),
      Chord(397, List(Tone(74,198,93))),
      Chord(587, List(Tone(78,208,93))),
      Chord(792, List(Tone(81,203,98))),
      Chord(995, List(Tone(79,192,95))),
      Chord(1192, List(Tone(78,206,86))),
      Chord(1396, List(Tone(76,200,81))),
      Chord(1594, List(Tone(74,192,95)))
    )),
    Bar(List(
      Chord(0, List(Tone(83,75,83), Tone(49,295,98), Tone(81,75,78))),
      Chord(135, List(Tone(81,331,107))),
      Chord(302, List(Tone(57,280,91))),
      Chord(446, List(Tone(81,83,81), Tone(79,83,100))),
      Chord(600, List(Tone(83,450,81), Tone(52,300,100))),
      Chord(898, List(Tone(57,300,79))),
      Chord(1046, List(Tone(79,154,93))),
      Chord(1205, List(Tone(78,578,100), Tone(50,302,86))),
      Chord(1492, List(Tone(57,300,98)))
    )),
    Bar(List(
      Chord(0, List(Tone(43,607,102), Tone(76,202,88), Tone(55,601,102))),
      Chord(210, List(Tone(71,195,76))),
      Chord(407, List(Tone(76,195,93))),
      Chord(595, List(Tone(79,203,96))),
      Chord(792, List(Tone(76,210,90))),
      Chord(1007, List(Tone(79,188,93))),
      Chord(1200, List(Tone(83,587,107)))
    )),
    Bar(List(
      Chord(0, List(Tone(76,152,96))),
      Chord(147, List(Tone(74,145,61))),
      Chord(300, List(Tone(73,152,85))),
      Chord(450, List(Tone(74,150,76))),
      Chord(602, List(Tone(71,589,102), Tone(55,203,91))),
      Chord(800, List(Tone(59,192,68))),
      Chord(1000, List(Tone(62,195,74))),
      Chord(1205, List(Tone(55,200,85))),
      Chord(1402, List(Tone(59,193,62))),
      Chord(1600, List(Tone(62,195,78)))
    )),
    Bar(List(
      Chord(0, List(Tone(55,577,95), Tone(78,152,100))),
      Chord(150, List(Tone(76,157,76))),
      Chord(300, List(Tone(79,150,90))),
      Chord(445, List(Tone(76,145,90))),
      Chord(601, List(Tone(57,1164,93), Tone(74,595,105), Tone(66,584,91))),
      Chord(1200, List(Tone(64,575,85), Tone(73,567,83)))
    )),
    Bar(List(
      Chord(0, List(Tone(50,1770,102))),
      Chord(296, List(Tone(66,303,81))),
      Chord(594, List(Tone(55,589,74), Tone(70,304,93))),
      Chord(895, List(Tone(71,304,96))),
      Chord(1195, List(Tone(74,306,98), Tone(59,585,81))),
      Chord(1495, List(Tone(78,294,100)))
    )),
    Bar(List(
      Chord(0, List(Tone(69,206,105))),
      Chord(206, List(Tone(73,203,83))),
      Chord(401, List(Tone(76,205,98))),
      Chord(601, List(Tone(61,594,85), Tone(49,584,85), Tone(79,448,98))),
      Chord(1049, List(Tone(76,149,90))),
      Chord(1200, List(Tone(62,578,91), Tone(78,445,102), Tone(50,584,91))),
      Chord(1657, List(Tone(74,141,107)))
    )),
    Bar(List(
      Chord(0, List(Tone(74,552,102), Tone(62,560,102), Tone(66,558,102), Tone(57,595,100))),
      Chord(595, List(Tone(76,159,102), Tone(67,590,102))),
      Chord(751, List(Tone(74,147,85))),
      Chord(898, List(Tone(73,147,91))),
      Chord(1055, List(Tone(74,135,100))),
      Chord(1202, List(Tone(74,572,107), Tone(62,578,107), Tone(66,580,107)))
    )),
    Bar(List(
      Chord(0, List(Tone(55,1180,98), Tone(43,1178,98), Tone(71,150,112))),
      Chord(155, List(Tone(75,154,90))),
      Chord(301, List(Tone(76,150,95))),
      Chord(446, List(Tone(79,150,93))),
      Chord(599, List(Tone(83,593,112))),
      Chord(1199, List(Tone(73,597,108), Tone(57,585,91), Tone(45,585,91)))
    )),
    Bar(List(
      Chord(0, List(Tone(67,303,112))),
      Chord(303, List(Tone(79,292,124))),
      Chord(603, List(Tone(58,565,88), Tone(55,567,88), Tone(78,296,113))),
      Chord(904, List(Tone(76,295,103))),
      Chord(1199, List(Tone(55,565,93), Tone(58,575,93), Tone(74,300,96))),
      Chord(1499, List(Tone(73,289,113)))
    )),
    Bar(List(
      Chord(0, List(Tone(74,1760,102), Tone(50,598,96))),
      Chord(603, List(Tone(45,452,91))),
      Chord(1050, List(Tone(42,149,79))),
      Chord(1204, List(Tone(38,580,86)))
    )),
    Bar(List(
      Chord(0, List(Tone(81,198,108))),
      Chord(195, List(Tone(78,203,86))),
      Chord(400, List(Tone(74,196,102))),
      Chord(598, List(Tone(42,579,91), Tone(54,594,91), Tone(69,599,107))),
      Chord(1200, List(Tone(50,572,88), Tone(74,591,117), Tone(38,585,88)))
    )),
    Bar(List(
      Chord(0, List(Tone(68,297,100))),
      Chord(297, List(Tone(74,280,108))),
      Chord(597, List(Tone(59,555,95), Tone(74,305,110), Tone(62,573,95))),
      Chord(893, List(Tone(73,291,108))),
      Chord(1199, List(Tone(73,300,108), Tone(59,575,88), Tone(62,575,88))),
      Chord(1496, List(Tone(71,290,108)))
    )),
    Bar(List(
      Chord(0, List(Tone(62,560,103), Tone(50,547,103))),
      Chord(296, List(Tone(78,293,100))),
      Chord(597, List(Tone(78,295,107), Tone(49,583,88), Tone(61,578,88))),
      Chord(893, List(Tone(76,297,95))),
      Chord(1194, List(Tone(59,570,95), Tone(76,302,102), Tone(47,560,95))),
      Chord(1497, List(Tone(74,290,107)))
    )),
    Bar(List(
      Chord(0, List(Tone(76,585,93))),
      Chord(594, List(Tone(63,580,96), Tone(78,147,100), Tone(59,585,96))),
      Chord(741, List(Tone(76,153,81))),
      Chord(886, List(Tone(78,153,73))),
      Chord(1039, List(Tone(81,152,91))),
      Chord(1196, List(Tone(64,557,96), Tone(52,560,96), Tone(79,579,98)))
    )),
    Bar(List(
      Chord(0, List(Tone(57,1194,91), Tone(66,582,91), Tone(74,632,91))),
      Chord(292, List(Tone(64,587,90), Tone(73,601,81))),
      Chord(601, List(Tone(62,564,93), Tone(50,567,91), Tone(74,583,95)))
    )),
    Bar(List(
      Chord(0, List(Tone(43,1165,100), Tone(55,1180,100), Tone(71,310,96))),
      Chord(295, List(Tone(74,310,96))),
      Chord(600, List(Tone(79,289,105))),
      Chord(906, List(Tone(83,263,91))),
      Chord(1198, List(Tone(42,586,91), Tone(81,590,95), Tone(54,586,91)))
    )),
    Bar(List(
      Chord(0, List(Tone(71,626,103), Tone(55,613,95))),
      Chord(599, List(Tone(43,590,83), Tone(83,195,98))),
      Chord(799, List(Tone(81,205,86))),
      Chord(991, List(Tone(79,215,81))),
      Chord(1199, List(Tone(78,211,91))),
      Chord(1393, List(Tone(76,207,78))),
      Chord(1603, List(Tone(74,195,71)))
    )),
    Bar(List(
      Chord(0, List(Tone(50,579,93), Tone(62,577,93), Tone(73,147,95))),
      Chord(147, List(Tone(71,160,90))),
      Chord(300, List(Tone(74,155,86))),
      Chord(445, List(Tone(71,147,74))),
      Chord(595, List(Tone(69,607,95), Tone(61,593,88), Tone(52,1188,88))),
      Chord(1194, List(Tone(59,586,95), Tone(68,598,96)))
    )),
    Bar(List(
      Chord(0, List(Tone(67,200,95))),
      Chord(191, List(Tone(79,212,96))),
      Chord(398, List(Tone(76,200,91))),
      Chord(591, List(Tone(73,206,90), Tone(57,578,90), Tone(64,606,90))),
      Chord(791, List(Tone(76,201,96))),
      Chord(1000, List(Tone(67,195,90))),
      Chord(1198, List(Tone(59,562,93), Tone(62,572,91), Tone(66,574,96)))
    )),
    Bar(List(
      Chord(0, List(Tone(55,1165,102), Tone(43,1186,100))),
      Chord(152, List(Tone(71,307,98))),
      Chord(296, List(Tone(75,310,113))),
      Chord(451, List(Tone(76,297,86))),
      Chord(595, List(Tone(79,294,95), Tone(40,565,93), Tone(52,589,93))),
      Chord(755, List(Tone(83,285,105)))
    )),
    Bar(List(
      Chord(0, List(Tone(49,567,98), Tone(61,567,98), Tone(81,298,105))),
      Chord(303, List(Tone(76,272,105))),
      Chord(594, List(Tone(76,261,102), Tone(59,558,88), Tone(47,570,86), Tone(78,38,79))),
      Chord(896, List(Tone(74,275,90))),
      Chord(1194, List(Tone(74,260,102), Tone(57,571,91), Tone(45,556,93), Tone(76,38,74))),
      Chord(1496, List(Tone(73,296,93)))
    )),
    Bar(List(
      Chord(0, List(Tone(76,165,102))),
      Chord(152, List(Tone(74,153,79))),
      Chord(300, List(Tone(73,152,96))),
      Chord(451, List(Tone(74,154,95))),
      Chord(604, List(Tone(69,1166,95), Tone(54,195,91))),
      Chord(801, List(Tone(57,203,74))),
      Chord(1004, List(Tone(62,205,93))),
      Chord(1201, List(Tone(54,205,88))),
      Chord(1394, List(Tone(57,202,76))),
      Chord(1596, List(Tone(62,200,79)))
    )),
    Bar(List(
      Chord(0, List(Tone(78,308,100), Tone(52,198,103))),
      Chord(191, List(Tone(79,312,90), Tone(55,201,81))),
      Chord(392, List(Tone(59,200,85))),
      Chord(596, List(Tone(52,198,91), Tone(76,604,93))),
      Chord(791, List(Tone(55,201,71))),
      Chord(992, List(Tone(59,200,83))),
      Chord(1200, List(Tone(52,198,98))),
      Chord(1391, List(Tone(55,201,78))),
      Chord(1592, List(Tone(59,193,88)))
    ))
  )
  
  val bars: List[Bar] = bars1 ::: bars2
}

class ScoreDatabase extends Actor {
  import ScoreDatabase._
  def receive = {
    case GetBar(num) =>
      println("Request for bar number " + num)
      sender ! bars(num)
  }
}
