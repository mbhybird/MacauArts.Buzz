/*
 Navicat Premium Data Transfer

 Source Server         : server
 Source Server Type    : MySQL
 Source Server Version : 50541
 Source Host           : 192.168.0.202
 Source Database       : beacondb

 Target Server Type    : MySQL
 Target Server Version : 50541
 File Encoding         : utf-8

 Date: 06/17/2015 18:35:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `beacon`
-- ----------------------------
DROP TABLE IF EXISTS `beacon`;
CREATE TABLE `beacon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `beaconid` varchar(20) DEFAULT NULL,
  `displayname` varchar(20) DEFAULT NULL,
  `major` int(11) DEFAULT NULL,
  `minor` int(11) DEFAULT NULL,
  `priority` tinyint(1) DEFAULT NULL,
  `effectiverangein` int(11) DEFAULT NULL,
  `effectiverangeout` int(11) DEFAULT NULL,
  `throughrange` int(11) DEFAULT NULL,
  `effectiverangein_back` int(11) DEFAULT NULL,
  `effectiverangeout_back` int(11) DEFAULT NULL,
  `throughrange_back` int(11) DEFAULT NULL,
  `extag` varchar(20) DEFAULT NULL,
  `usage` varchar(20) DEFAULT NULL,
  `rangedirection` varchar(20) DEFAULT NULL,
  `angle` decimal(4,1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `beacon`
-- ----------------------------
BEGIN;
INSERT INTO `beacon` VALUES ('1', '2397-1', 'b001', '2397', '1', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MacauArts.01', 'detail', 'both', '100.0'), ('2', '2397-2', 'b002', '2397', '2', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MacauArts.01', 'detail', 'both', '100.0'), ('3', '2397-3', 'b003', '2397', '3', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MacauArts.01', 'detail', 'both', '100.0'), ('4', '2397-4', 'b004', '2397', '4', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MacauArts.01', 'detail', 'both', '100.0'), ('5', '2397-6', 'b006', '2397', '6', '1', '-50', '-50', '-70', '-70', '-70', '-70', 'MacauArts.01', 'introduction', 'both', '100.0');
COMMIT;

-- ----------------------------
--  Table structure for `beaconaction`
-- ----------------------------
DROP TABLE IF EXISTS `beaconaction`;
CREATE TABLE `beaconaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `beaconid` varchar(20) DEFAULT NULL,
  `triggerid` int(11) DEFAULT NULL,
  `contentid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `beaconaction`
-- ----------------------------
BEGIN;
INSERT INTO `beaconaction` VALUES ('1', '2397-1', '1', '1'), ('2', '2397-1', '1', '5'), ('3', '2397-2', '1', '1'), ('4', '2397-2', '1', '5'), ('5', '2397-3', '1', '2'), ('6', '2397-3', '1', '6'), ('7', '2397-4', '1', '2'), ('8', '2397-4', '1', '6'), ('9', '2397-6', '1', '3'), ('10', '2397-6', '1', '7');
COMMIT;

-- ----------------------------
--  Table structure for `content`
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contenttype` tinyint(1) DEFAULT NULL,
  `serverpath` varchar(100) DEFAULT NULL,
  `clientpath` varchar(100) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `description_cn` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `description_en` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `description_tw` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `description_pt` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `title_cn` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `title_en` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `title_tw` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `title_pt` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `artist_cn` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `artist_en` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `artist_tw` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `artist_pt` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `year` smallint(6) DEFAULT NULL,
  `extag` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `content`
-- ----------------------------
BEGIN;
INSERT INTO `content` VALUES ('1', '2', 'http://192.168.0.106:8080/exhibition/image/p1.png', '/com.buzz.exhibition/MacauArts.01/image/', 'p1.png', '“水宫日出时的美丽景象。我想要同时拍到宫殿和另外两幢水中建筑，所以我使用了超广角镜头。很少有人在黎明时来这里参观，所以在早晨水宫看起来一片安宁祥和，当地人在早晨会来喂食鸽子和池塘中的鱼。鸽子们一齐飞过湖面，填满了我的镜头。”', '\"Beautiful view of JalMahal during sunrise. I wanted to capture the palace and other two water structures also in the frame. Thats why I have used an ultra wide angle lens to capture this frame. Not many tourists visit the place at dawn, so the location is bit peaceful early in the morning. And locals visit the lake in the morning and feed the pigeons and fish in pond. These pigeons keeps flying across the lake once in a while, which I used to fill my frame.\"', '「水宮日の出の時に美しい光景。私と同時撮影宮殿と他の2棟の建物を使用した水の中から、超広角レンズ。少しのある人は夜明けにここに来て見学するので、朝水宮一面の安寧に見える穏やかで、地元の人は朝が来ると池の中の魚を鳩。ハトたちは一体になって飛ぶことが湖面に埋めて私のレンズ。」', '\"Bela Vista do jalmahal Durante o Nascer do sol.EU Queria capturar o palácio e outras duas estruturas de água também no Quadro.É por isso que EU usei UMA ultra - lente Grande angular para capturar o frame.Não há muitos turistas visitam o local Durante a Madrugada, então a localização é pouco tranquila no início Da manhã.Habitantes e visitar a Lagoa de manhã e alimentar OS pombos e OS peixes no Lago.Estes pombos continua a sobrevoar o Lago de vez EM Quando, que EU costumava encher MEU frame \".', '永恒的微笑', 'Eternal smile', '永遠の微笑', 'O eterno Sorriso', '梵高', null, null, null, null, null), ('2', '2', 'http://192.168.0.106:8080/exhibition/image/p2.png', '/com.buzz.exhibition/MacauArts.01/image/', 'p2.png', '“在拍到这张照片的前一个晚上，我们花了一整天时间想要在危险的白犀牛群中拍到一张好照片。我们偷偷摸摸地穿过草地，小心翼翼地试图呆在30英尺以外的安全地带，但是没有拍到我想要的照片。第二天早上，当我醒来却看到3只白犀牛在我面前吃草，于是拍下了这张照片。”', '\"The night before this photo, we tried all day to get a good photo of the endangered white rhino.Skulking through the grass, carefully trying to stay 30 feet away to be safe, didn\'t provide me the photo I was hoping for. In the morning, however, I woke up to all three rhinos grazing in front of me.\"', '「撮っ写真の前の夜、私たちは一日中時間かかったと危険な白サイの群の中で撮っ写真。私たちはこそこそと芝生を横切っていて、慎重にしようとする３０フィート以外の安全地帯が写っていません私の要った写真。翌日の朝、目が覚めるが3匹の白サイ私の前に草を食べ、そしてで撮られた写真。」', '\"A Noite antes desta foto, tentei o dia todo para conseguir UMA boa foto do ameaçado rinoceronte Branco. Vagueando PELA relva, cuidadosamente, tentando ficar 30 metros para ser Seguro, não me fornecer a foto que EU esperava.De manhã, acordei às três rinocerontes pastando EM Frente de MIM \".', '最后的晚餐', 'Last supper', '最後の晩餐', 'A última CEIA.', '达芬奇', null, null, null, null, null), ('3', '2', 'http://192.168.0.106:8080/exhibition/image/p3.png', '/com.buzz.exhibition/MacauArts.01/image/', 'p3.png', '“随着科技的发展，世界正变得越来越小，而想找到真正的野外也变得越来越难，这让这些独有的时刻和景象变得更加特别。攀上崖壁是一项不小的挑战，因为并没有什么开辟出的通路。在我看来，优山美地的这一景象美的让人窒息，这无疑是给予那些已经准备好想要和自然有一个亲密接触的人的奖励。”', '\"As technology shrinks the world around us, it becomes more and more difficult to find ourselves truly lost in the outdoors. This makes those particular moments and scenes that much more special. Getting to the Diving Board was quite a challenge, as there is no official trail. For anybody who is prepared, careful, and respectful of nature, the reward is one of the most breathtaking views in all of Yosemite, in my opinion.\"', '「科学技術の発展とともに、世界が狭く、を見つけたい本当の野外も難しくなり、これらの特有の時刻と天気がさらにスペシャル。崖壁登るのは一つの小さなを挑むが、別にに開発された通路。私から見れば、優山美のこの光景に美しい人を窒息させて、これは間違いなく与えたいあれらの準備ができて自然との親密な接触の人の奨励。」', '\"Como a tecnologia diminui o Mundo que NOS rodeia, Torna - se Mais e Mais difícil de encontrar - NOS verdadeiramente Perdido no exterior.Isso Torna esses Momentos e cenas Muito Mais especial.A prancha FOI um Grande Desafio, Como não há trilha Oficial.Para quem está preparado, cuidadoso e respeitoso Da natureza, a recompensa é UMA Das Mais deslumbrantes vistas de Yosemite EM tudo, Na minha opinião. \"', '向日葵', 'Sunflower', 'ひまわり', 'Girassol', '毕加索', null, null, null, null, null), ('4', '2', 'http://192.168.0.106:8080/exhibition/image/p4.png', '/com.buzz.exhibition/MacauArts.01/image/', 'p4.png', '“在夏威夷可爱岛的那帕里海岸，巨浪翻滚。在西北岸潮涌刚刚开始的季节，加上秋日的阳光造就了这一美景。但是真正让我觉得特别的是，一只鸟儿从镜头前飞过，在画面的一角。这一生命的小小瞬间让图片看起来更加和谐，也让我想起：平凡成就卓越。”', '\"Giant waves converge and jump together along the Na Pali coast of Kauai. An early season northwest swell and the position of the autumn sun made this shot possible, but what really makes it special to me is the bird flying in the corner of the frame. This little moment of life adds balance to the image and reminds me that the mundane often [makes] the spectacular.\"', '「ハワイで可愛い島の帕里海岸、大シケ。北西岸が始まったばかりの季節に加え、秋の陽光が生み出したこの景色。でも、本当に私は特別な感じで、鳥がカメラの前に飛んだから、画面の一角。この生命の小さな瞬間を写真には見えより調和のとれたも思い出し：平凡成就抜群。」', '\"Ondas Gigantes convergem e saltar juntos Ao Longo da Costa de Na Pali de Kauai.Um início de Temporada do swell e a posição do sol de outono feito isto possível, MAS o que realmente Torna - se especial para MIM é o pássaro a voar no canto do Quadro.Esse momento Da Vida adiciona equilíbrio para a Imagem e me FAZ lembrar que o mundano, muitas vezes, [FAZ] O espetacular \".', '入睡的维纳斯', 'Sleep Venus', '眠りのビーナス', 'Para dormir.', null, null, null, null, null, null), ('5', '1', 'http://192.168.0.106:8080/exhibition/audio/a1.mp3', '/com.buzz.exhibition/MacauArts.01/audio/', 'a1.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('6', '1', 'http://192.168.0.106:8080/exhibition/audio/a2.mp3', '/com.buzz.exhibition/MacauArts.01/audio/', 'a2.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('7', '1', 'http://192.168.0.106:8080/exhibition/audio/a3.mp3', '/com.buzz.exhibition/MacauArts.01/audio/', 'a3.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, null), ('8', '1', 'http://192.168.0.106:8080/exhibition/audio/a4.mp3', '/com.buzz.exhibition/MacauArts.01/audio/', 'a4.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `exmaster`
-- ----------------------------
DROP TABLE IF EXISTS `exmaster`;
CREATE TABLE `exmaster` (
  `extag` varchar(100) NOT NULL,
  `title_cn` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `title_en` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `title_tw` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `title_pt` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `description_cn` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `description_en` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `description_tw` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `description_pt` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `expireddate` date DEFAULT NULL,
  `userid` varchar(20) DEFAULT NULL,
  `contentid` int(11) DEFAULT NULL,
  PRIMARY KEY (`extag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `exmaster`
-- ----------------------------
BEGIN;
INSERT INTO `exmaster` VALUES ('MacauArts.01', '世界名画', 'The world famous paintings', '世界の名画', 'A pintura Famosa do Mundo', '“在夏威夷可爱岛的那帕里海岸，巨浪翻滚。在西北岸潮涌刚刚开始的季节，加上秋日的阳光造就了这一美景。但是真正让我觉得特别的是，一只鸟儿从镜头前飞过，在画面的一角。这一生命的小小瞬间让图片看起来更加和谐，也让我想起：平凡成就卓越。”', '\"Giant waves converge and jump together along the Na Pali coast of Kauai. An early season northwest swell and the position of the autumn sun made this shot possible, but what really makes it special t', '「ハワイで可愛い島の帕里海岸、大シケ。北西岸が始まったばかりの季節に加え、秋の陽光が生み出したこの景色。でも、本当に私は特別な感じで、鳥がカメラの前に飛んだから、画面の一角。この生命の小さな瞬間を写真には見えより調和のとれたも思い出し：平凡成就抜群。」', '\"Ondas Gigantes convergem e saltar juntos Ao Longo da Costa de Na Pali de Kauai.Um início de Temporada do swell e a posição do sol de outono feito isto possível, MAS o que realmente Torna - se especia', '2015-07-01', 'nick.chung', '4'), ('MacauArts.02', '中国名画', 'China paintings', '中国の名画', 'China Pinturas', '“在夏威夷可爱岛的那帕里海岸，巨浪翻滚。在西北岸潮涌刚刚开始的季节，加上秋日的阳光造就了这一美景。但是真正让我觉得特别的是，一只鸟儿从镜头前飞过，在画面的一角。这一生命的小小瞬间让图片看起来更加和谐，也让我想起：平凡成就卓越。”', '\"Giant waves converge and jump together along the Na Pali coast of Kauai. An early season northwest swell and the position of the autumn sun made this shot possible, but what really makes it special t', '「ハワイで可愛い島の帕里海岸、大シケ。北西岸が始まったばかりの季節に加え、秋の陽光が生み出したこの景色。でも、本当に私は特別な感じで、鳥がカメラの前に飛んだから、画面の一角。この生命の小さな瞬間を写真には見えより調和のとれたも思い出し：平凡成就抜群。」', '\"Ondas Gigantes convergem e saltar juntos Ao Longo da Costa de Na Pali de Kauai.Um início de Temporada do swell e a posição do sol de outono feito isto possível, MAS o que realmente Torna - se especia', '2015-07-02', 'nick.chung', '1'), ('MacauArts.03', '欧洲名画', 'Eur paintings', '欧洲の名画', 'Eur Pinturas', '“在夏威夷可爱岛的那帕里海岸，巨浪翻滚。在西北岸潮涌刚刚开始的季节，加上秋日的阳光造就了这一美景。但是真正让我觉得特别的是，一只鸟儿从镜头前飞过，在画面的一角。这一生命的小小瞬间让图片看起来更加和谐，也让我想起：平凡成就卓越。”', '\"Giant waves converge and jump together along the Na Pali coast of Kauai. An early season northwest swell and the position of the autumn sun made this shot possible, but what really makes it special t', '「ハワイで可愛い島の帕里海岸、大シケ。北西岸が始まったばかりの季節に加え、秋の陽光が生み出したこの景色。でも、本当に私は特別な感じで、鳥がカメラの前に飛んだから、画面の一角。この生命の小さな瞬間を写真には見えより調和のとれたも思い出し：平凡成就抜群。」', '\"Ondas Gigantes convergem e saltar juntos Ao Longo da Costa de Na Pali de Kauai.Um início de Temporada do swell e a posição do sol de outono feito isto possível, MAS o que realmente Torna - se especia', '2015-07-02', 'nick.chung', '2');
COMMIT;

-- ----------------------------
--  Table structure for `syslog`
-- ----------------------------
DROP TABLE IF EXISTS `syslog`;
CREATE TABLE `syslog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(10) DEFAULT NULL,
  `beaconid` varchar(50) DEFAULT NULL,
  `logtime` datetime DEFAULT NULL,
  `triggertype` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `sysparams`
-- ----------------------------
DROP TABLE IF EXISTS `sysparams`;
CREATE TABLE `sysparams` (
  `id` int(11) NOT NULL,
  `paramgroup` varchar(20) DEFAULT NULL,
  `paramkey` tinyint(4) DEFAULT NULL,
  `paramvalue` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `sysparams`
-- ----------------------------
BEGIN;
INSERT INTO `sysparams` VALUES ('1', 'contenttype', '0', 'video'), ('2', 'contenttype', '1', 'audio'), ('3', 'contenttype', '2', 'image'), ('4', 'triggertype', '0', 'in'), ('5', 'triggertype', '1', 'out'), ('6', 'triggertype', '2', 'stay'), ('7', 'triggertype', '3', 'through'), ('8', 'voicelang', '0', 'cc'), ('9', 'voicelang', '1', 'sc'), ('10', 'voicelang', '2', 'en'), ('11', 'voicelang', '3', 'pt'), ('12', 'usage', '0', 'introduction'), ('13', 'usage', '1', 'detail'), ('14', 'rangedirection', '0', 'front'), ('15', 'rangedirection', '1', 'back'), ('16', 'displaylang', '0', 'tw'), ('17', 'displaylang', '1', 'cn'), ('18', 'displaylang', '2', 'en'), ('19', 'displaylang', '3', 'pt');
COMMIT;

-- ----------------------------
--  Table structure for `trigger`
-- ----------------------------
DROP TABLE IF EXISTS `trigger`;
CREATE TABLE `trigger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `triggertype` tinyint(4) DEFAULT NULL,
  `triggercount` tinyint(4) DEFAULT NULL,
  `triggerfrequency` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `trigger`
-- ----------------------------
BEGIN;
INSERT INTO `trigger` VALUES ('1', '0', '1', '500'), ('2', '1', '1', '500'), ('3', '2', '5', '500');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `username_cn` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `username_en` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `username_tw` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `username_pt` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `isauthorized` tinyint(1) DEFAULT '1',
  `defaultlang` tinyint(1) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `voicelang` tinyint(1) DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'nick.chung', 'nick_简', 'nick_en', 'nick_繁', 'nick_pt', '1', '0', '123456', 'IronMan_钢佚侠', '0', 'nick@itzbuzz.com'), ('2', 'cody.lam', 'cody_简', 'cody_en', 'cody_繁', 'cody_pt', '1', '1', '123456', 'SpiderMan_蜘蛛侠', '3', 'cody@itzbuzz.com'), ('11', 'abc@itz.com', null, null, null, null, '1', '1', '123456', 'abc', '1', 'abc@itz.com');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
