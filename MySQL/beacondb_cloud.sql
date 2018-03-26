/*
 Navicat Premium Data Transfer

 Source Server         : cloud
 Source Server Type    : MySQL
 Source Server Version : 50541
 Source Host           : www.things.buzz
 Source Database       : beacondb

 Target Server Type    : MySQL
 Target Server Version : 50541
 File Encoding         : utf-8

 Date: 06/17/2015 18:34:42 PM
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `beacon`
-- ----------------------------
BEGIN;
INSERT INTO `beacon` VALUES ('1', '2397-1', 'b001', '2397', '1', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'detail', 'both', '100.0'), ('2', '2397-2', 'b002', '2397', '2', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'detail', 'both', '100.0'), ('3', '2397-3', 'b003', '2397', '3', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'detail', 'both', '100.0'), ('4', '2397-4', 'b004', '2397', '4', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'detail', 'both', '100.0'), ('5', '2397-6', 'b006', '2397', '6', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'introduction', 'both', '100.0'), ('7', '2397-7', 'b007', '2397', '7', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'detail', 'both', '267.0'), ('8', '2397-8', 'b008', '2397', '8', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'detail', 'both', '267.0'), ('9', '2397-9', 'b009', '2397', '9', '1', '-45', '-50', '-70', '-70', '-70', '-70', 'MaA.A', 'detail', 'both', '267.0');
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `beaconaction`
-- ----------------------------
BEGIN;
INSERT INTO `beaconaction` VALUES ('1', '2397-1', '1', '1'), ('2', '2397-1', '1', '5'), ('3', '2397-2', '1', '2'), ('4', '2397-2', '1', '6'), ('5', '2397-3', '1', '9'), ('6', '2397-3', '1', '10'), ('7', '2397-6', '1', '2'), ('8', '2397-6', '1', '6'), ('9', '2397-4', '1', '11'), ('10', '2397-4', '1', '12'), ('11', '2397-7', '1', '9'), ('12', '2397-7', '1', '10'), ('13', '2397-8', '1', '11'), ('14', '2397-8', '1', '12'), ('15', '2397-9', '1', '13'), ('16', '2397-9', '1', '14');
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `content`
-- ----------------------------
BEGIN;
INSERT INTO `content` VALUES ('1', '2', 'http://things.buzz/download/MaA.A/image/tree.png', '/com.buzz.exhibition/MaA.A/image/', 'tree.png', '妈祖阁,主奉海神妈祖，是位于澳门半岛西南方的标志性建筑物之一，现为澳门三大古刹中最古老者。2005年作为澳门历史城区的部分被列入世界文化遗产名录内。\r\n\r\n妈阁庙始建于1488年，时值明朝。当时妈阁庙门前已为澳门之海岸线，是当地及附近居住之渔民作业之上岸补给、歇息和祈福之处。庙宇背山面海，沿崖建筑，古木参天，风光优美。整座庙宇包括大殿（又称正觉禅林）、石殿（又称神山第一殿）、弘仁殿、观音阁等4座主要建筑。石狮镇门、飞檐凌空，是一座富有中国文化特色的古建筑。\r\n\r\n经过庙门及花岗石牌坊，便是庭院，循着山麓的石阶小径，拾级而上，即可抵达建于巉岩巨石间、就石窟凿成的弘仁殿。殿内四壁，雕刻着海魔神将，色彩斑斓，中央供奉天后。自弘仁殿至观音阁，沿着山崖有不少石刻，或为名流政要咏题，或为骚人墨客遣兴，楷草篆隶，诸体俱备。观音阁位于庙之最高处，供奉观音菩萨。不少西洋画家亦曾描绘了庙前繁华景象，而妈阁庙亦出现在最早一批在中国拍摄的照片\r\n妈阁庙始建于1488年，时值明朝。当时妈阁庙门前已为澳门之海岸线，是当地及附近居住之渔民作业之上岸补给、歇息和祈福之处。庙宇背山面海，沿崖建筑，古木参天，风光优美。整座庙宇包括大殿（又称正觉禅林）、石殿（又称神山第一殿）、弘仁殿、观音阁等4座主要建筑。石狮镇门、飞檐凌空，是一座富有中国文化特色的古建筑。\r\n\r\n经过庙门及花岗石牌坊，便是庭院，循着山麓的石阶小径，拾级而上，即可抵达建于巉岩巨石间、就石窟凿成的弘仁殿。殿内四壁，雕刻着海魔神将，色彩斑斓，中央供奉天后。自弘仁殿至观音阁，沿着山崖有不少石刻，或为名流政要咏题，或为骚人墨客遣兴，楷草篆隶，诸体俱备。观音阁位于庙之最高处，供奉观音菩萨。不少西洋画家亦曾描绘了庙前繁华景象，而妈阁庙亦出现在最早一批在中国拍摄的照片', 'The name Macau is thought to be derived from the name of the temple. It is said that when the Portuguese sailors landed at the coast just outside the temple and asked the name of the place, the natives replied \"媽閣\" (Jyutping: \"Maa1 Gok3\") or \"A-Ma-Gau\" (bay of goddess A-Ma). The Portuguese then named the peninsula \"Macao\".[1] The temple was well described in ancient Chinese texts, as well as represented in paintings, related to Macao. It is also one of the first scenes photographed in Macao.\r\n\r\nTemple consists of six main parts:[2] Gate Pavilion, the Memorial Arch, the Prayer Hall, the Hall of Benevolence (the oldest part of the temple), the Hall of Guanyin, Zhengjiao Chanlin - Buddhist Pavilion.\r\n\r\nIn 2005, the temple became one of the designated sites of the Historic Centre of Macau enlisted on UNESCO World Heritage List.', '媽祖閣,主奉海神媽祖，是位於澳門半島西南方的標誌性建築物之一，現為澳門三大古剎中最古老者。2005年作為澳門歷史城區的部分被列入世界文化遺產名錄內。\r\n\r\n媽閣廟始建於1488年，時值明朝。當時媽閣廟門前已為澳門之海岸線，是當地及附近居住之漁民作業之上岸補給、歇息和祈福之處。廟宇背山面海，沿崖建築，古木參天，風光優美。整座廟宇包括大殿（又稱正覺禪林）、石殿（又稱神山第一殿）、弘仁殿、觀音閣等4座主要建築。石獅鎮門、飛檐凌空，是一座富有中國文化特色的古建築。\r\n\r\n經過廟門及花崗石牌坊，便是庭院，循着山麓的石階小徑，拾級而上，即可抵達建於巉岩巨石間、就石窟鑿成的弘仁殿。殿內四壁，雕刻着海魔神將，色彩斑斕，中央供奉天后。自弘仁殿至觀音閣，沿着山崖有不少石刻，或為名流政要詠題，或為騷人墨客遣興，楷草篆隸，諸體俱備。觀音閣位於廟之最高處，供奉觀音菩薩。不少西洋畫家亦曾描繪了廟前繁華景象，而媽閣廟亦出現在最早一批在中國拍攝的照片。', 'Templo de A-Má, que se localiza à entrada do Porto Interior (no extremo-sul da Península de Macau), a meio da encosta poente da Colina da Barra, já existia antes da própria Cidade de Macau ter nascido. Especula-se que o templo foi construído pelos pescadores chineses residentes de Macau no séc. XV, para homenagear e adorar a Deusa A-Má (Deusa do Céu), chamada também de Tin Hau, Mazu ou Matsu. Esta divindade taoísta é muito venerada em todo o Sul da China e em várias partes do Sudeste Asiático e é considerada como a protectora dos pescadores e marinheiros. Crê-se que os portugueses desembarcaram pela primeira vez em Macau, possivelmente no ano de 1554 ou 1557, precisamente à entrada do Porto Interior, também chamada pelos pescadores chineses de \"Baía de A-Má\". Segundo as lendas do séc. XVI, o nome da Cidade deriva precisamente da palavra em cantonense \"A-Má-Gau\", que significa literalmente Baía de A-Má.', '妈祖阁正殿前', 'Interior of the A-Ma Temple, Macao', '媽祖閣正殿前', 'Interior do Templo deA-Má, Macau', '奥古士丁·博尔杰', 'Auguste Borget', '奧古士丁·博爾傑', 'Auguste Borget', '1842', 'MaA.A'), ('2', '2', 'http://things.buzz/download/MaA.A/image/sailboat.png', '/com.buzz.exhibition/MaA.A/image/', 'sailboat.png', '外国商船最早于澳门北湾作为停泊商港，其堤岸为半环形。到清同治年间，内港已成为北湾及浅湾的统称，乃环形港口。由于货运依赖水路，内港一带早已为繁荣地区。据《澳门图记》载述：“其西洋舶既入十字门者，又须由小十字门折而至南湾，又折而至娘妈角，然后抵澳。其水路至香山，须易小艇，夷舶不可到也。”由此看来，其天然水道浅窄。1868年，澳葡政府将北湾填塞，并筑成一道直堤小路，旁边筑有拱形铺楼。1923年，政府再填海扩阔沿堤马路和兴建码头，使内港成为现代化商港。[3]自中国实行改革开放后，内港发展为重要的海路交通枢纽，是连接香港及中国内地运输中心和渔业港口。', 'The former Yutong pier was part of the Ponte 16 project. The no. 14 pier was demolished and the new terminal at no. 11A was built to replace it. In order to reduce high speed ferries\' influence on the other harbour users, the Macau - Shekou and Macau - Jiangmen services were moved to Taipa Ferry Terminal from 24 September 2009 and 29 January 2010 respectively. Today the terminal only provide one cross-harbour service between Macau and Zhuhai.', '外國商船最早於澳門北灣作為停泊商港，其隄岸為半環形。到清同治年間，內港已成為北灣及淺灣的統稱，乃環形港口。由於貨運依賴水路，內港一帶早已為繁榮地區。據《澳門圖記》載述：「其西洋舶既入十字門者，又須由小十字門折而至南灣，又折而至娘媽角，然後抵澳。其水路至香山，須易小艇，夷舶不可到也。」由此看來，其天然水道淺窄。1868年，澳葡政府將北灣填塞，並築成一道直隄小路，旁邊築有拱形鋪樓。1923年，政府再填海擴闊沿隄馬路和興建碼頭，使內港成為現代化商港。[3]自中國實行改革開放後，內港發展為重要的海路交通樞紐，是連接香港及中國內地運輸中心和漁業港口。', 'Macau desempenhou papel fundamental nas relações entre o Ocidente e a China por ter sido, até 1685, o único porto aberto por este império ao comércio exterior, o que transformou Macau, por mais de um século, no centro do tráfego marítimo e comercial e de intercâmbio de culturas entre o Ocidente e o Oriente.', '澳门内港', 'The Inner Harbour,Macao', '澳門內港', '​Porto Interior, Macau', '佚名中国画家', 'UnidentifiedChinese', '佚名中國畫家', 'Artista chinêsanónim', '1840', 'MaA.A'), ('3', '2', 'http://things.buzz/download/MaA.A/image/macau.png', '/com.buzz.exhibition/MaA.A/image/', 'macau.png', '封面.Capa!!!!!', '', '', '', '', '', '', '', null, null, null, null, null, 'MaA.A'), ('4', '2', 'http://things.buzz/download/MaA.A/image/fish_house.png', '/com.buzz.exhibition/MaA.A/image/', 'fish_house.png', '澳门大炮台建于明神宗年间（公元1617年），原为耶稣会大三巴教堂（即大三巴牌坊的前身）的祭天台，保护耶稣会在澳门的产业，后被澳葡政府转为军事防御设施的大炮台城堡。大炮台的建筑历时10年，1626年才完成。大炮台与妈阁炮台和东望洋炮台组成一道坚固的外围军事防线，以防范海盗之用。从1623年至1740年间，曾为城防司令和澳门总督官邸。', 'Fortaleza do Monte (Portuguese for Mount Fortress, also Monte Forte; officially Fortaleza de Nossa Senhora do Monte de São Paulo, in English: Fortress of Our Lady of the Mount of St. Paul; Chinese: 大炮台) is the historical military centre of the former Portuguese colony of Macau, in the People\'s Republic of China. It is part of the \"Historic Centre of Macau\", a UNESCO World Heritage Site.[1]', '澳門大炮台建於明神宗年間（公元1617年），原為耶穌會大三巴教堂（即大三巴牌坊的前身）的祭天台，保護耶穌會在澳門的產業，後被澳葡政府轉為軍事防禦設施的大炮台城堡。大炮台的建築歷時10年，1626年才完成。大炮台與媽閣炮台和東望洋炮台組成一道堅固的外圍軍事防線，以防範海盜之用。從1623年至1740年間，曾為城防司令和澳門總督官邸。', 'No contexto dos ataques da Companhia Neerlandesa das Índias Orientais a Macau entre 1603 e 1622 , a fortificação remonta a uma cerca erguida pelos religiosos da Companhia de Jesus para defesa do monte de São Paulo, concluída por volta de 1606 . Posteriormente, entre 1617 e 1626 , em posição dominante sobre o seu cume, foi erguida a chamada Fortaleza do Monte, como parte de um vasto complexo que integrava o Colégio e a Igreja de São Paulo .', '从大炮台俯视澳门景色', 'View of Macaofrom the Mount Fortress,Macao', '從大炮臺俯視澳門景色', '​Macau Visto da Fortalezado Monte, Macau', '托马斯·屈臣', 'Thomas Watson', '托馬斯·屈臣', 'Thomas Watson', '1852', 'MaA.A'), ('5', '1', 'http://things.buzz/download/MaA.A/audio/tree.mp3', '/com.buzz.exhibition/MaA.A/audio/', 'music_tree.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'MaA.A'), ('6', '1', 'http://things.buzz/download/MaA.A/audio/sailboat.mp3', '/com.buzz.exhibition/MaA.A/audio/', 'music_sailboat.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'MaA.A'), ('7', '1', 'http://things.buzz/download/MaA.A/audio/fish_house.mp3', '/co/com.buzz.exhibition/MaA.A/audio/', 'music_fish_house.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'MaA.A'), ('8', '1', 'http://things.buzz/download/MaA.A/audio/shark.mp3', '/com.buzz.exhibition/MaA.A/audio/', 'music_shark.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'MaA.A'), ('9', '2', 'http://things.buzz/download/MaA.A/image/woman.png', '/com.buzz.exhibition/MaA.A/image/', 'woman.png', '作者拍攝了幾位認識多年的朋友，照片展示他們的個人生活方式或閒暇興趣。除外，也邀請了數位來自不同領域的專業人士進行拍攝，影像呈現各人的工作特色和文化背景。作者以單一或多重合成影像，刻畫人物的個人特質和專長。', 'Wong Ho Sang photographed several of his long-time friends, revealing their life style or leisure interests. He also invited a number of professionals from various fields to join the project, showing the specialty of work and cultural background of the participants. The author used single or multiple images to depict individual quality and expertise of his photographic subjects.', '作者拍摄了几位认识多年的朋友，照片展示他们的个人生活方式或闲暇兴趣。除外，也邀请了数位来自不同领域的专业人士进行拍摄，影像呈现各人的工作特色和文化背景。作者以单一或多重合成影像，刻画人物的个人特质和专长。', 'Wong Ho Sang fotografou vários amigos de longa data, revelando o seu estilo de vida e interesses. Também convidou profissionais de diversas áreas para se juntarem ao projecto, revelando a especificidade do trabalho e a formação cultural dos participantes. O artista utilizou uma ou várias imagens para retratar as suas qualidades individuais e habilidades.', '故事', 'STORY', '故事', 'HISTÓRIAS', '黄豪生', 'Wong Ho Sang', '黃豪生', 'Wong Ho Sang', '1982', 'MaA.A'), ('10', '1', 'http://things.buzz/download/MaA.A/audio/woman.mp3', '/com.buzz.exhibition/MaA.A/audio/', 'music_woman.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'MaA.A'), ('11', '2', 'http://things.buzz/download/MaA.A/image/cat.png', '/com.buzz.exhibition/MaA.A/image/', 'cat.png', '本系列作品借着对比当代、高科技与历史、简朴的建筑结构特色，探讨古今文化和艺术在二十一世纪的共存空间。通过并列对照不同的建筑风格和物料，结合立体实物与平面影像，以展示创新和传统间的差异和矛盾。', '“Structure” series investigates the common ground of existence between conventional and contemporary arts and culture in the 21st century, by contrasting hi-tech and historic architectural characteristics, and revealing the differences and contradictions of invention and tradition through juxtaposition of various architectural materials and styles, combining three-dimensional objects and photographic images. ', '本系列作品藉着對比當代、高科技與歷史、簡樸的建築結構特色，探討古今文化和藝術在二十一世紀的共存空間。通過並列對照不同的建築風格和物料，結合立體實物與平面影像，以展示創新和傳統間的差異和矛盾。', 'Esta série pesquisa o terreno comum da coexistência entre a arte e a cultura convencional e contemporânea do século XXI, através do contraste da tecnologia e das características históricas da arquitectura E, revela as diferenças e contradições da invenção e tradição através da justaposição de vários materiais e estilos arquitectónicos, combinando objectos tridimensionais e imagens fotográficas. ', '结构', 'STRUCTURE', '結構', 'STRUCTURE', '黄豪生', 'Wong Ho Sang', '黃豪生', 'Wong Ho Sang', '1984', 'MaA.A'), ('12', '1', 'http://things.buzz/download/MaA.A/audio/cat.mp3', '/com.buzz.exhibition/MaA.A/audio/', 'music_cat.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'MaA.A'), ('13', '2', 'http://things.buzz/download/MaA.A/image/shark.png', '/com.buzz.exhibition/MaA.A/image/', 'shark.png', '本系列拍摄了在澳门生活的各行各业的人物。作者选择了富历史意义的岗顶剧院舞台为拍摄场地，并以单调古朴的墙壁为背景。每件作品皆结合了三张照片──人物、中式和欧式建筑内景。照片都经过繁复的传统暗房技法处理，包括中途曝光、影像重叠和过棕色调。完成的作品综合了文字、图像、建筑和人物，以多层面和多角度的表现手法反映每位人物的个人特质', '\"INTERIORS\" series photographed people of various occupations who lived in Macau. Wong Ho Sang chose the historic Dom Pedro Theatre as the site for photography sessions, and he used the simple wall of the stage as the background. Each triptych combines portrait, interiors of Chinese and European architectures. Photographs utilized complicated and traditional darkroom techniques, including solarization, multiple images printing and toning. The works incorporate text、diagrams、architectures and portraitures, reflecting the personal traits of each individual with multi-layered sensibilities. ', '本系列拍攝了在澳門生活的各行各業的人物。作者選擇了富歷史意義的崗頂劇院舞臺為拍攝場地，並以單調古樸的牆壁為背景。每件作品皆結合了三張照片──人物、中式和歐式建築內景。照片都經過繁複的傳統暗房技法處理，包括中途曝光、影像重疊和過棕色調。完成的作品綜合了文字、圖像、建築和人物，以多層面和多角度的表現手法反映每位人物的個人特質', 'Série de fotografias sobre pessoas de Macau e seus diferentes ofícios. Wong Ho Sang escolheu o histórico teatro D.Pedro V para cenário deste projecto, utilizando uma simples parede do palco como fundo. Cada tríptico conjuga retratos e interiores dos edifícios chineses e portugueses. As fotografias foram obtidas utilizando técnicas fotográficas complexas em conjunto com técnicas mais tradicionais, incluindo iluminação, impressão e tonalização de imagens múltiplas. O trabalho inclui textos, diagramas, arquitectura e retratos, reflectindo as características pessoais de cada indivíduo com diferentes níveis de sensibilidade.', '內', 'INTERIORES', '內', 'INTERIORES', '黄豪生', 'Wong Ho Sang', '黃豪生', 'Wong Ho Sang', '1983', 'MaA.A'), ('14', '1', 'http://things.buzz/download/MaA.A/audio/shark.mp3', '/com.buzz.exhibition/MaA.A/audio/', 'music_shark.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'MaA.A'), ('15', '2', 'http://things.buzz/download/MaA.C/images/RestaurantSolmar.png', '/com.buzz.exhibition/MaA.C/image/', 'Rest.png', '封面.Capa!!!!!', '', '', '', '', '', '', '', null, null, null, null, null, 'MaA.C'), ('16', '1', 'http://things.buzz/download/MaA.B/images/mgm.png', '/com.buzz.exhibition/MaA.B/image/', 'mgm.png', '封面.Capa!!!!!', '', '', '', '', '', '', '', null, null, null, null, null, 'MaA.B');
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
INSERT INTO `exmaster` VALUES ('MaA.A', '澳门艺术(测试用)', 'MacauArts(testing)', '澳門藝術(測試用)', 'MacauArts(testing)', '澳门是欧洲艺术最早传入中国的地点，也是中国西洋画的发源地，以保存大量外销画和风俗画著称，其美术历程具有特殊性。 十八世纪后半叶至十九世纪中叶，不少西洋画家如英国钱纳利、法国博尔杰、苏格兰屈臣医生等来到澳门，描述了当时澳门及邻近地区的生活境况，与本地外销画匠共同创造了一个极具韵味的澳门美术时期。', 'During the 18th Century, a large number of the West European painters arrived at Chinese coastal cities due to intensive trade between China and the Mediterranean countries in Europe. ', '澳門是歐洲藝術最早傳入中國的地點，也是中國西洋畫的發源地，以保存大量外銷畫和風俗畫著稱，其美術歷程具有特殊性。十八世紀後半葉至十九世紀中葉，不少西洋畫家如英國錢納利、法國博爾傑、蘇格蘭屈臣醫生等來到澳門，描述了當時澳門及鄰近地區的生活境況，與本地外銷畫匠共同創造了一個極具韻味的澳門美術時期。', 'Durante o século XVIII, um grande número do Ocidente europeu pintores chegaram às cidades costeiras chinesas, devido ao intenso comércio entre a China e os países mediterrânicos na Europa.', '2015-10-01', 'nick.chung', '3'), ('MaA.B', '澳门美高梅艺术装置展', 'A NEW CHAPTER IN CHINA-PORTUGAL CULTURAL EXCHANGE\r\nJOANA VASCONCELOS AT MGM MACAU', '澳門美高梅藝術裝置展', 'UM NOVO CAPÍTULO EM INTERCÂMBIO CULTURAL DE CHINA-PORTUGAL', '澳门美高梅一直以举办具前瞻性及富创意的美艺盛事为傲，打造澳门成为国际艺术之都。踏入2015年，澳门美高梅特别邀请葡萄牙著名当代艺术家乔安娜 • 瓦思康丝勒 (Joana Vasconcelos)，展出巨型艺术装置「八面灵龙」（Valkyrie Octopus）。以非凡的意念，触动人心，并进一步推动中葡文化的交流，同时展示澳门作为世界旅游文化中心之魅力。', 'At the forefront in introducing the very finest in artistic events and exhibitions, MGM MACAU has reinforced the territory’s reputation as a key international city where globally influential artists a', '澳門美高梅一直以舉辦具前瞻性及富創意的美藝盛事為傲，打造澳門成為國際藝術之都。踏入2015年，澳門美高梅特別邀請葡萄牙著名當代藝術家瓊安娜 • 瓦思康絲勒 (Joana Vasconcelos)，展出巨型藝術裝置「八面靈龍」（Valkyrie Octopus）。以非凡的意念，觸動人心，並進一步推動中葡文化的交流，同時展示澳門作為世界旅遊文化中心之魅力。', 'Na vanguarda em apresentando o melhor da exposições e eventos artísticos, MGM MACAU reforçou a reputação do território como uma cidade internacional chave onde são apreciados globalmente influentes ar', '2015-10-01', 'nick.chung', '16'), ('MaA.C', '肖像油画', 'Portrait Oil Paintings', '肖像油畫', 'Pinturas a Óleo Retrato', '肖像艺术泛指人类对自身或特定个体形象所进行的诠释记录，涵盖立体、平面，如雕塑、绘画及影像等类型的创作，包括头像、胸像、半身像、全身像等。目前最早可追溯至公元前二百年左右的埃及彩绘石雕。', 'Portraiture, in general, refers to an interpretation and record of oneself or specific individual image created by a human being, which employs three dimensions or two dimensions, such as sculpture, p', '肖像藝術泛指人類對自身或特定個體形象所進行的詮釋記錄，涵蓋立體、平面，如雕塑、繪畫及影像等類型的創作，包括頭像、胸像、半身像、全身像等。目前最早可追溯至公元前二百年左右的埃及彩繪石雕。', 'Em termos gerais ‘retrato’ refere-se à representação da imagem de alguém em duas ou três dimensões através da escultura, pintura, fotografia etc. e assume a forma de cabeça, busto, de meio ou corpo in', '2015-10-01', 'nick.chung', '15'), ('MaA.D', null, null, null, null, null, null, null, null, null, null, null), ('MaA.F', null, null, null, null, null, null, null, null, null, null, null);
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
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'nick.chung', 'nick_简', 'nick_en', 'nick_繁', 'nick_pt', '1', '0', '123456', 'IronMan_钢佚侠', '0', 'nick.chung'), ('2', 'cody.lam', 'cody_简', 'cody_en', 'cody_繁', 'cody_pt', '1', '1', '123456', 'SpiderMan_蜘蛛侠', '3', ''), ('13', 'edd@anc.com', null, null, null, null, '1', '1', '123', 'edd', '0', 'edd@anc.com'), ('26', 'sem', null, null, null, null, '1', '1', 's', 's', '0', 'sem'), ('27', '', null, null, null, null, '1', '1', '', '', '0', ''), ('28', '', null, null, null, null, '1', '1', '', '', '0', ''), ('30', 'f', null, null, null, null, '1', '1', 't', 'r', '1', 'f'), ('31', 'f', null, null, null, null, '1', '1', 't', 'r', '1', 'f'), ('32', 'f', null, null, null, null, '1', '0', 'f', 'f', '0', 'f'), ('33', 'f', null, null, null, null, '1', '0', 'f', 'f', '0', 'f'), ('35', 'hg', null, null, null, null, '1', '0', '123', 'ql', '0', 'hg'), ('37', 'abc@itz.com', null, null, null, null, '1', '1', '123456', 'abc', '1', 'abc@itz.com'), ('38', 't', null, null, null, null, '1', '0', 'h', 'j', '0', 't'), ('39', 'y', null, null, null, null, '1', '0', 'y', 't', '0', 'y'), ('40', 't', null, null, null, null, '1', '1', 'f', 'r', '3', 't'), ('41', 'r', null, null, null, null, '1', '2', 'r', 'r', '1', 'r'), ('42', 'c', null, null, null, null, '1', '2', 'c', 'f', '2', 'c'), ('43', 'dd', null, null, null, null, '1', '2', 'ddd', 'cc', '2', 'dd'), ('44', 'dd', null, null, null, null, '1', '2', 'ddd', 'cc', '2', 'dd'), ('45', 'dd', null, null, null, null, '1', '2', 'ddd', 'cc', '2', 'dd'), ('46', 'dd', null, null, null, null, '1', '2', 'ddd', 'cc', '2', 'dd'), ('47', 'dd', null, null, null, null, '1', '2', 'ddd', 'cc', '2', 'dd'), ('48', 'dd', null, null, null, null, '1', '2', 'ddd', 'cc', '2', 'dd'), ('49', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('50', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('51', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('52', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('53', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('54', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('55', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('56', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('57', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd'), ('58', 'fdd', null, null, null, null, '1', '2', 'ddd', 'ff', '2', 'fdd');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
