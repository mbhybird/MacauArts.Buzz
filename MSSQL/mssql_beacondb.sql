/*
 Navicat Premium Data Transfer

 Source Server         : webuser
 Source Server Type    : SQL Server
 Source Server Version : 10501600
 Source Host           : macauarts.buzz
 Source Database       : beacondb_test
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 10501600
 File Encoding         : utf-8

 Date: 08/14/2015 17:44:31 PM
*/

-- ----------------------------
--  Table structure for appuser
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[appuser]') AND type IN ('U'))
	DROP TABLE [dbo].[appuser]
GO
CREATE TABLE [dbo].[appuser] (
	[userid] nvarchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[defaultlang] tinyint NULL,
	[password] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[nickname] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[voicelang] tinyint NULL,
	[email] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of appuser
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[appuser] VALUES ('123@qq$com', '1', '123', 'long', '1', '123@qq.com');
INSERT INTO [dbo].[appuser] VALUES ('623@q$com', '1', 'sk', 'sk', '1', '623@q.com');
INSERT INTO [dbo].[appuser] VALUES ('a', '1', '123', 'a', '1', 'a@a.com');
INSERT INTO [dbo].[appuser] VALUES ('a@s', '1', 's', 's', '1', 'a@s');
INSERT INTO [dbo].[appuser] VALUES ('cody', '1', 'cody', 'cody', '1', 'cody');
INSERT INTO [dbo].[appuser] VALUES ('cody@things$buzz', '1', '1234', 'cody', '1', 'cody@things$buzz');
INSERT INTO [dbo].[appuser] VALUES ('d@', '1', 'fff', 'xc', '1', 'd@');
INSERT INTO [dbo].[appuser] VALUES ('df.ddf', '1', 'gbb', 'df', '1', 'df.ddf');
INSERT INTO [dbo].[appuser] VALUES ('eddie@things.buzz', '2', '123456', 'eddie', '2', 'eddie@things.buzz');
INSERT INTO [dbo].[appuser] VALUES ('ff@ff', '1', 'ffff', 'xxc', '0', 'ff@ff');
INSERT INTO [dbo].[appuser] VALUES ('ghj@', '2', 'ddtgh', 'tff', '2', 'ghj@');
INSERT INTO [dbo].[appuser] VALUES ('S@qq$com', '1', 'd', 'D', '1', 'S@qq.com');
INSERT INTO [dbo].[appuser] VALUES ('s@s', '1', 's', 's@s', '1', 's@s');
INSERT INTO [dbo].[appuser] VALUES ('s@sq', '1', 's', 's', '1', 's@sq');
INSERT INTO [dbo].[appuser] VALUES ('sam2D1D23f', '64', 'sample string 2', 'sample string 3', '64', 'samD1DFDFDng 4');
INSERT INTO [dbo].[appuser] VALUES ('test', '1', 'e', 'test', '3', 'test@test.com');
INSERT INTO [dbo].[appuser] VALUES ('test@things$buzz', '1', 'test', 'test', '1', 'test@things.buzz');
INSERT INTO [dbo].[appuser] VALUES ('Test@things$com', '1', '123456', 'Test', '1', 'Test@things.buzz');
INSERT INTO [dbo].[appuser] VALUES ('tui@h', '2', 'ryuu', 'tyy', '2', 'tui@h');
INSERT INTO [dbo].[appuser] VALUES ('yu@h', '0', 'yu', 'yu@h', '0', 'yu@h');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for appversion
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[appversion]') AND type IN ('U'))
	DROP TABLE [dbo].[appversion]
GO
CREATE TABLE [dbo].[appversion] (
	[id] int IDENTITY(1,1) NOT NULL,
	[version] varchar(10) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[url] varchar(200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[description] nvarchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[url_server] varchar(200) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[publishedversion] uniqueidentifier NOT NULL DEFAULT (newid())
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of appversion
-- ----------------------------
BEGIN TRANSACTION
GO
SET IDENTITY_INSERT [dbo].[appversion] ON
GO
INSERT INTO [dbo].[appversion] ([id], [version], [url], [description], [url_server], [publishedversion]) VALUES ('1', '0.9.0', 'http://192.168.0.202/apk/artsbuzz_090.apk', N'检测到最新版本，请及时更新！', 'http://macauarts.buzz:90/download/xml/version.xml', '7C3E9AD6-0F89-4B85-AD59-0ACF087726FB');
GO
SET IDENTITY_INSERT [dbo].[appversion] OFF
GO
COMMIT
GO

-- ----------------------------
--  Table structure for beacon
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[beacon]') AND type IN ('U'))
	DROP TABLE [dbo].[beacon]
GO
CREATE TABLE [dbo].[beacon] (
	[beaconid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[displayname] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[major] int NULL,
	[minor] int NULL,
	[priority] tinyint NULL,
	[effectiverangein] int NULL,
	[effectiverangeout] int NULL,
	[throughrange] int NULL,
	[effectiverangein_back] int NULL,
	[effectiverangeout_back] int NULL,
	[throughrange_back] int NULL,
	[usage] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[rangedirection] varchar(20) COLLATE Chinese_PRC_CI_AS NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of beacon
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[beacon] VALUES ('2397-1', 'A01', '2397', '1', '1', '-47', '-54', '-70', '-49', '-54', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-10', 'H10', '2397', '10', '1', '-48', '-54', '-70', '-48', '-54', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-11', 'H11', '2397', '11', '1', '-48', '-54', '-70', '-48', '-54', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-2', 'B02', '2397', '2', '1', '-52', '-55', '-70', '-52', '-55', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-3', 'C03', '2397', '3', '1', '-52', '-55', '-70', '-51', '-54', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-4', 'D04', '2397', '4', '1', '-47', '-54', '-70', '-47', '-54', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-6', 'E06', '2397', '6', '1', '-48', '-56', '-70', '-48', '-56', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-7', 'F07', '2397', '7', '1', '-60', '-70', '-70', '-60', '-70', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-8', 'G08', '2397', '8', '1', '-60', '-70', '-70', '-60', '-70', '-70', '1', '2');
INSERT INTO [dbo].[beacon] VALUES ('2397-9', 'H09', '2397', '9', '1', '-60', '-70', '-70', '-60', '-70', '-70', '1', '2');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for beaconaction
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[beaconaction]') AND type IN ('U'))
	DROP TABLE [dbo].[beaconaction]
GO
CREATE TABLE [dbo].[beaconaction] (
	[beaconid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[triggerid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[contentid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of beaconaction
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[beaconaction] VALUES ('2397-1', 't01', 'c01');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-1', 't01', 'c05');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-10', 't01', 'c09');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-10', 't01', 'c10');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-11', 't01', 'c02');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-11', 't01', 'c06');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-2', 't01', 'c17');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-2', 't01', 'c19');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-3', 't01', 'c18');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-3', 't01', 'c20');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-4', 't01', 'c25');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-4', 't01', 'c26');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-6', 't01', 'c04');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-6', 't01', 'c07');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-7', 't01', 'c21');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-7', 't01', 'c22');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-8', 't01', 'c23');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-8', 't01', 'c24');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-9', 't01', 'c13');
INSERT INTO [dbo].[beaconaction] VALUES ('2397-9', 't01', 'c14');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for beaconuuid
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[beaconuuid]') AND type IN ('U'))
	DROP TABLE [dbo].[beaconuuid]
GO
CREATE TABLE [dbo].[beaconuuid] (
	[id] int IDENTITY(1,1) NOT NULL,
	[target_uuid] uniqueidentifier NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of beaconuuid
-- ----------------------------
BEGIN TRANSACTION
GO
SET IDENTITY_INSERT [dbo].[beaconuuid] ON
GO
INSERT INTO [dbo].[beaconuuid] ([id], [target_uuid]) VALUES ('1', 'DD3D9DE3-1AD5-45C2-AF35-CA9A7B92A4ED');
INSERT INTO [dbo].[beaconuuid] ([id], [target_uuid]) VALUES ('2', 'B9407F30-F5F8-466E-AFF9-25556B57FE6D');
INSERT INTO [dbo].[beaconuuid] ([id], [target_uuid]) VALUES ('3', '23A01AF0-232A-4518-9C0E-323FB773F5EF');
INSERT INTO [dbo].[beaconuuid] ([id], [target_uuid]) VALUES ('4', 'E2C56DB5-DFFB-48D2-B060-D0F5A71096E0');
GO
SET IDENTITY_INSERT [dbo].[beaconuuid] OFF
GO
COMMIT
GO

-- ----------------------------
--  Table structure for content
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[content]') AND type IN ('U'))
	DROP TABLE [dbo].[content]
GO
CREATE TABLE [dbo].[content] (
	[contenttype] tinyint NULL,
	[serverpath] varchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[clientpath] varchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[filename] varchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[description_cn] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[description_en] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[description_tw] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[description_pt] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[title_cn] nvarchar(200) COLLATE Chinese_PRC_CI_AS NULL,
	[title_en] nvarchar(200) COLLATE Chinese_PRC_CI_AS NULL,
	[title_tw] nvarchar(200) COLLATE Chinese_PRC_CI_AS NULL,
	[title_pt] nvarchar(200) COLLATE Chinese_PRC_CI_AS NULL,
	[artist_cn] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[artist_en] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[artist_tw] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[artist_pt] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[year] smallint NULL,
	[contentid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[range] tinyint NOT NULL DEFAULT ((1)),
	[groupid] uniqueidentifier NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of content
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/tree.png', '/com.buzz.exhibition/maa/images/', 'tree.png', N'妈祖阁,主奉海神妈祖，是位于澳门半岛西南方的标志性建筑物之一，现为澳门三大古刹中最古老者。2005年作为澳门历史城区的部分被列入世界文化遗产名录内。

妈阁庙始建于1488年，时值明朝。当时妈阁庙门前已为澳门之海岸线，是当地及附近居住之渔民作业之上岸补给、歇息和祈福之处。庙宇背山面海，沿崖建筑，古木参天，风光优美。整座庙宇包括大殿（又称正觉禅林）、石殿（又称神山第一殿）、弘仁殿、观音阁等4座主要建筑。石狮镇门、飞檐凌空，是一座富有中国文化特色的古建筑。

经过庙门及花岗石牌坊，便是庭院，循着山麓的石阶小径，拾级而上，即可抵达建于巉岩巨石间、就石窟凿成的弘仁殿。殿内四壁，雕刻着海魔神将，色彩斑斓，中央供奉天后。自弘仁殿至观音阁，沿着山崖有不少石刻，或为名流政要咏题，或为骚人墨客遣兴，楷草篆隶，诸体俱备。观音阁位于庙之最高处，供奉观音菩萨。不少西洋画家亦曾描绘了庙前繁华景象，而妈阁庙亦出现在最早一批在中国拍摄的照片
妈阁庙始建于1488年，时值明朝。当时妈阁庙门前已为澳门之海岸线，是当地及附近居住之渔民作业之上岸补给、歇息和祈福之处。庙宇背山面海，沿崖建筑，古木参天，风光优美。整座庙宇包括大殿（又称正觉禅林）、石殿（又称神山第一殿）、弘仁殿、观音阁等4座主要建筑。石狮镇门、飞檐凌空，是一座富有中国文化特色的古建筑。

经过庙门及花岗石牌坊，便是庭院，循着山麓的石阶小径，拾级而上，即可抵达建于巉岩巨石间、就石窟凿成的弘仁殿。殿内四壁，雕刻着海魔神将，色彩斑斓，中央供奉天后。自弘仁殿至观音阁，沿着山崖有不少石刻，或为名流政要咏题，或为骚人墨客遣兴，楷草篆隶，诸体俱备。观音阁位于庙之最高处，供奉观音菩萨。不少西洋画家亦曾描绘了庙前繁华景象，而妈阁庙亦出现在最早一批在中国拍摄的照片', N'The name Macau is thought to be derived from the name of the temple. It is said that when the Portuguese sailors landed at the coast just outside the temple and asked the name of the place, the natives replied "媽閣" (Jyutping: "Maa1 Gok3") or "A-Ma-Gau" (bay of goddess A-Ma). The Portuguese then named the peninsula "Macao".[1] The temple was well described in ancient Chinese texts, as well as represented in paintings, related to Macao. It is also one of the first scenes photographed in Macao.

Temple consists of six main parts:[2] Gate Pavilion, the Memorial Arch, the Prayer Hall, the Hall of Benevolence (the oldest part of the temple), the Hall of Guanyin, Zhengjiao Chanlin - Buddhist Pavilion.

In 2005, the temple became one of the designated sites of the Historic Centre of Macau enlisted on UNESCO World Heritage List.', N'媽祖閣,主奉海神媽祖，是位於澳門半島西南方的標誌性建築物之一，現為澳門三大古剎中最古老者。2005年作為澳門歷史城區的部分被列入世界文化遺產名錄內。

媽閣廟始建於1488年，時值明朝。當時媽閣廟門前已為澳門之海岸線，是當地及附近居住之漁民作業之上岸補給、歇息和祈福之處。廟宇背山面海，沿崖建築，古木參天，風光優美。整座廟宇包括大殿（又稱正覺禪林）、石殿（又稱神山第一殿）、弘仁殿、觀音閣等4座主要建築。石獅鎮門、飛檐凌空，是一座富有中國文化特色的古建築。

經過廟門及花崗石牌坊，便是庭院，循着山麓的石階小徑，拾級而上，即可抵達建於巉岩巨石間、就石窟鑿成的弘仁殿。殿內四壁，雕刻着海魔神將，色彩斑斕，中央供奉天后。自弘仁殿至觀音閣，沿着山崖有不少石刻，或為名流政要詠題，或為騷人墨客遣興，楷草篆隸，諸體俱備。觀音閣位於廟之最高處，供奉觀音菩薩。不少西洋畫家亦曾描繪了廟前繁華景象，而媽閣廟亦出現在最早一批在中國拍攝的照片。', 'Templo de A-Má, que se localiza à entrada do Porto Interior (no extremo-sul da Península de Macau), a meio da encosta poente da Colina da Barra, já existia antes da própria Cidade de Macau ter nascido. Especula-se que o templo foi construído pelos pescadores chineses residentes de Macau no séc. XV, para homenagear e adorar a Deusa A-Má (Deusa do Céu), chamada também de Tin Hau, Mazu ou Matsu. Esta divindade taoísta é muito venerada em todo o Sul da China e em várias partes do Sudeste Asiático e é considerada como a protectora dos pescadores e marinheiros. Crê-se que os portugueses desembarcaram pela primeira vez em Macau, possivelmente no ano de 1554 ou 1557, precisamente à entrada do Porto Interior, também chamada pelos pescadores chineses de "Baía de A-Má". Segundo as lendas do séc. XVI, o nome da Cidade deriva precisamente da palavra em cantonense "A-Má-Gau", que significa literalmente Baía de A-Má.', N'妈祖阁正殿前', 'Interior of the A-Ma Temple, Macao', N'媽祖閣正殿前', 'Interior do Templo deA-Má, Macau', N'奥古士丁·博尔杰', 'Auguste Borget', N'奧古士丁·博爾傑', 'Auguste Borget', '1842', 'c01', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/sailboat.png', '/com.buzz.exhibition/maa/images/', 'sailboat.png', N'外国商船最早于澳门北湾作为停泊商港，其堤岸为半环形。到清同治年间，内港已成为北湾及浅湾的统称，乃环形港口。由于货运依赖水路，内港一带早已为繁荣地区。据《澳门图记》载述：“其西洋舶既入十字门者，又须由小十字门折而至南湾，又折而至娘妈角，然后抵澳。其水路至香山，须易小艇，夷舶不可到也。”由此看来，其天然水道浅窄。1868年，澳葡政府将北湾填塞，并筑成一道直堤小路，旁边筑有拱形铺楼。1923年，政府再填海扩阔沿堤马路和兴建码头，使内港成为现代化商港。[3]自中国实行改革开放后，内港发展为重要的海路交通枢纽，是连接香港及中国内地运输中心和渔业港口。', 'The former Yutong pier was part of the Ponte 16 project. The no. 14 pier was demolished and the new terminal at no. 11A was built to replace it. In order to reduce high speed ferries'' influence on the other harbour users, the Macau - Shekou and Macau - Jiangmen services were moved to Taipa Ferry Terminal from 24 September 2009 and 29 January 2010 respectively. Today the terminal only provide one cross-harbour service between Macau and Zhuhai.', N'外國商船最早於澳門北灣作為停泊商港，其隄岸為半環形。到清同治年間，內港已成為北灣及淺灣的統稱，乃環形港口。由於貨運依賴水路，內港一帶早已為繁榮地區。據《澳門圖記》載述：「其西洋舶既入十字門者，又須由小十字門折而至南灣，又折而至娘媽角，然後抵澳。其水路至香山，須易小艇，夷舶不可到也。」由此看來，其天然水道淺窄。1868年，澳葡政府將北灣填塞，並築成一道直隄小路，旁邊築有拱形鋪樓。1923年，政府再填海擴闊沿隄馬路和興建碼頭，使內港成為現代化商港。[3]自中國實行改革開放後，內港發展為重要的海路交通樞紐，是連接香港及中國內地運輸中心和漁業港口。', 'Macau desempenhou papel fundamental nas relações entre o Ocidente e a China por ter sido, até 1685, o único porto aberto por este império ao comércio exterior, o que transformou Macau, por mais de um século, no centro do tráfego marítimo e comercial e de intercâmbio de culturas entre o Ocidente e o Oriente.', N'澳门内港', 'The Inner Harbour,Macao', N'澳門內港', N'​Porto Interior, Macau', N'佚名中国画家', 'UnidentifiedChinese', N'佚名中國畫家', 'Artista chinêsanónim', '1840', 'c02', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/macau.png', '/com.buzz.exhibition/maa/images/', 'macau.png', N'封面.Capa!!!!!', '', '', '', '', '', '', '', null, null, null, null, null, 'c03', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/fish_house.png', '/com.buzz.exhibition/maa/images/', 'fish_house.png', N'澳门大炮台建于明神宗年间（公元1617年），原为耶稣会大三巴教堂（即大三巴牌坊的前身）的祭天台，保护耶稣会在澳门的产业，后被澳葡政府转为军事防御设施的大炮台城堡。大炮台的建筑历时10年，1626年才完成。大炮台与妈阁炮台和东望洋炮台组成一道坚固的外围军事防线，以防范海盗之用。从1623年至1740年间，曾为城防司令和澳门总督官邸。', N'Fortaleza do Monte (Portuguese for Mount Fortress, also Monte Forte; officially Fortaleza de Nossa Senhora do Monte de São Paulo, in English: Fortress of Our Lady of the Mount of St. Paul; Chinese: 大炮台) is the historical military centre of the former Portuguese colony of Macau, in the People''s Republic of China. It is part of the "Historic Centre of Macau", a UNESCO World Heritage Site.[1]', N'澳門大炮台建於明神宗年間（公元1617年），原為耶穌會大三巴教堂（即大三巴牌坊的前身）的祭天台，保護耶穌會在澳門的產業，後被澳葡政府轉為軍事防禦設施的大炮台城堡。大炮台的建築歷時10年，1626年才完成。大炮台與媽閣炮台和東望洋炮台組成一道堅固的外圍軍事防線，以防範海盜之用。從1623年至1740年間，曾為城防司令和澳門總督官邸。', 'No contexto dos ataques da Companhia Neerlandesa das Índias Orientais a Macau entre 1603 e 1622 , a fortificação remonta a uma cerca erguida pelos religiosos da Companhia de Jesus para defesa do monte de São Paulo, concluída por volta de 1606 . Posteriormente, entre 1617 e 1626 , em posição dominante sobre o seu cume, foi erguida a chamada Fortaleza do Monte, como parte de um vasto complexo que integrava o Colégio e a Igreja de São Paulo .', N'从大炮台俯视澳门景色', 'View of Macaofrom the Mount Fortress,Macao', N'從大炮臺俯視澳門景色', N'​Macau Visto da Fortalezado Monte, Macau', N'托马斯·屈臣', 'Thomas Watson', N'托馬斯·屈臣', 'Thomas Watson', '1852', 'c04', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_tree.mp3', '/com.buzz.exhibition/maa/audio/', 'music_tree.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c05', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_sailboat.mp3', '/com.buzz.exhibition/maa/audio/', 'music_sailboat.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c06', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_fish_house.mp3', '/com.buzz.exhibition/maa/audio/', 'music_fish_house.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c07', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/woman.png', '/com.buzz.exhibition/maa/images/', 'woman.png', N'作者拍攝了幾位認識多年的朋友，照片展示他們的個人生活方式或閒暇興趣。除外，也邀請了數位來自不同領域的專業人士進行拍攝，影像呈現各人的工作特色和文化背景。作者以單一或多重合成影像，刻畫人物的個人特質和專長。', 'Wong Ho Sang photographed several of his long-time friends, revealing their life style or leisure interests. He also invited a number of professionals from various fields to join the project, showing the specialty of work and cultural background of the participants. The author used single or multiple images to depict individual quality and expertise of his photographic subjects.', N'作者拍摄了几位认识多年的朋友，照片展示他们的个人生活方式或闲暇兴趣。除外，也邀请了数位来自不同领域的专业人士进行拍摄，影像呈现各人的工作特色和文化背景。作者以单一或多重合成影像，刻画人物的个人特质和专长。', 'Wong Ho Sang fotografou vários amigos de longa data, revelando o seu estilo de vida e interesses. Também convidou profissionais de diversas áreas para se juntarem ao projecto, revelando a especificidade do trabalho e a formação cultural dos participantes. O artista utilizou uma ou várias imagens para retratar as suas qualidades individuais e habilidades.', N'故事', 'STORY', N'故事', 'HISTÓRIAS', N'黄豪生', 'Wong Ho Sang', N'黃豪生', 'Wong Ho Sang', '1982', 'c09', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_woman.mp3', '/com.buzz.exhibition/maa/audio/', 'music_woman.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c10', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/cat.png', '/com.buzz.exhibition/maa/images/', 'cat.png', N'本系列作品借着对比当代、高科技与历史、简朴的建筑结构特色，探讨古今文化和艺术在二十一世纪的共存空间。通过并列对照不同的建筑风格和物料，结合立体实物与平面影像，以展示创新和传统间的差异和矛盾。', N'“Structure” series investigates the common ground of existence between conventional and contemporary arts and culture in the 21st century, by contrasting hi-tech and historic architectural characteristics, and revealing the differences and contradictions of invention and tradition through juxtaposition of various architectural materials and styles, combining three-dimensional objects and photographic images. ', N'本系列作品藉着對比當代、高科技與歷史、簡樸的建築結構特色，探討古今文化和藝術在二十一世紀的共存空間。通過並列對照不同的建築風格和物料，結合立體實物與平面影像，以展示創新和傳統間的差異和矛盾。', 'Esta série pesquisa o terreno comum da coexistência entre a arte e a cultura convencional e contemporânea do século XXI, através do contraste da tecnologia e das características históricas da arquitectura E, revela as diferenças e contradições da invenção e tradição através da justaposição de vários materiais e estilos arquitectónicos, combinando objectos tridimensionais e imagens fotográficas. ', N'结构', 'STRUCTURE', N'結構', 'STRUCTURE', N'黄豪生', 'Wong Ho Sang', N'黃豪生', 'Wong Ho Sang', '1984', 'c11', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_cat.mp3', '/com.buzz.exhibition/maa/audio/', 'music_cat.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c12', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/port.png', '/com.buzz.exhibition/maa/images/', 'port.png', N'本系列拍摄了在澳门生活的各行各业的人物。作者选择了富历史意义的岗顶剧院舞台为拍摄场地，并以单调古朴的墙壁为背景。每件作品皆结合了三张照片──人物、中式和欧式建筑内景。照片都经过繁复的传统暗房技法处理，包括中途曝光、影像重叠和过棕色调。完成的作品综合了文字、图像、建筑和人物，以多层面和多角度的表现手法反映每位人物的个人特质', N'"INTERIORS" series photographed people of various occupations who lived in Macau. Wong Ho Sang chose the historic Dom Pedro Theatre as the site for photography sessions, and he used the simple wall of the stage as the background. Each triptych combines portrait, interiors of Chinese and European architectures. Photographs utilized complicated and traditional darkroom techniques, including solarization, multiple images printing and toning. The works incorporate text、diagrams、architectures and portraitures, reflecting the personal traits of each individual with multi-layered sensibilities. ', N'本系列拍攝了在澳門生活的各行各業的人物。作者選擇了富歷史意義的崗頂劇院舞臺為拍攝場地，並以單調古樸的牆壁為背景。每件作品皆結合了三張照片──人物、中式和歐式建築內景。照片都經過繁複的傳統暗房技法處理，包括中途曝光、影像重疊和過棕色調。完成的作品綜合了文字、圖像、建築和人物，以多層面和多角度的表現手法反映每位人物的個人特質', 'Série de fotografias sobre pessoas de Macau e seus diferentes ofícios. Wong Ho Sang escolheu o histórico teatro D.Pedro V para cenário deste projecto, utilizando uma simples parede do palco como fundo. Cada tríptico conjuga retratos e interiores dos edifícios chineses e portugueses. As fotografias foram obtidas utilizando técnicas fotográficas complexas em conjunto com técnicas mais tradicionais, incluindo iluminação, impressão e tonalização de imagens múltiplas. O trabalho inclui textos, diagramas, arquitectura e retratos, reflectindo as características pessoais de cada indivíduo com diferentes níveis de sensibilidade.', N'內', 'INTERIORES', N'內', 'INTERIORES', N'黄豪生', 'Wong Ho Sang', N'黃豪生', 'Wong Ho Sang', '1983', 'c13', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_port.mp3', '/com.buzz.exhibition/maa/audio/', 'music_port.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c14', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/mac/images/Rest.png', '/com.buzz.exhibition/mac/images/', 'Rest.png', N'封面.Capa!!!!!', '', '', '', '', '', '', '', null, null, null, null, null, 'c15', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/mab/images/mgm.png', '/com.buzz.exhibition/mab/images/', 'mgm.png', N'封面.Capa!!!!!', '', '', '', '', '', '', '', null, null, null, null, null, 'c16', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/mab/images/mgm_color.png', '/com.buzz.exhibition/mab/images/', 'mgm_color.png', N'巨型艺术装置「八面灵龙」（Valkyrie Octopus）于美高梅天幕广场盛大展出！以非凡的意念，触动人心，并进一步推动中葡文化的交流，同时展示澳门作为世界旅游文化中心之魅力', 'Giant art installation "eight spirit Dragon" (Valkyrie Octopus) displayed at the MGM Grand Atrium Plaza Grand! With extraordinary ideas, touching, and further promote the cultural exchanges between China and Portugal and a showcase of Macao as an attractive tourist and cultural center of the world', N'巨型藝術裝置「八面靈龍」（Valkyrie Octopus）于美高梅天幕廣場盛大展出！ 以非凡的意念，觸動人心，並進一步推動中葡文化的交流，同時展示澳門作為世界旅遊文化中心之魅力', 'Instalação de arte gigante "oito Espírito Dragão" (polvo Valquíria) exibido no MGM Grand Atrium Plaza Grand! Com idéias extraordinárias, tocando e promover o intercâmbio cultural entre a China e Portugal e uma vitrine de Macau como um atrativo turístico e centro cultural do mundo', N'八面灵龙-色彩', 'Valkyrie Octopus', N'八面靈龍-色彩', 'Valkyrie Octopus', N'乔安娜 • 瓦思康丝勒', 'Joana Vasconcelos', N'瓊安娜 • 瓦思康絲勒', 'Joana Vasconcelos', '2014', 'c17', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/mab/images/mgm_flower.png', '/com.buzz.exhibition/mab/images/', 'mgm_flower.png', N'瓦思康丝勒更创作了Tetris系列的Chiado、Alfama及Madragoa三座大型坐地雕塑，让参观者置身其中，可从不同的角度，欣赏整个艺术装置。此三件雕塑均以葡萄牙的主要历史地区命名。作品并使用了葡萄牙著名的手工花砖（Azulejos），绘上当地常见和西班牙摩尔式（Hispano-Moresque）图案及主题。以雕塑及画作，呈现葡萄牙深厚的历史 。', N'The artist offers visitors the opportunity to become part of the artwork with three large-scale floor sculptures from the Tetris series: Chiado, Alfama and Madragoa. Viewers could sit and contemplate the exhibition that envelops them from three perspectives. The titles refer to Lisbon’s main historic neighborhoods and the works are clad in handmade Azulejos tiles completed with motifs and themes common in Portuguese and Hispano-Moresque tile work. The Tetris series of sculptures and paintings perfectly reflects Portuguese history. ', N'瓦思康絲勒更創作了Tetris系列的Chiado、Alfama及Madragoa三座大型坐地雕塑，讓參觀者置身其中，可從不同的角度，欣賞整個藝術裝置。 此三件雕塑均以葡萄牙的主要歷史地區命名。 作品並使用了葡萄牙著名的手工花磚（Azulejos），繪上當地常見和西班牙摩爾式（Hispano-Moresque）圖案及主題。 以雕塑及畫作，呈現葡萄牙深厚的歷史 。', 'A artista oferece aos visitantes a oportunidade de fazer parte da arte-final com três esculturas de chão em grande escala da série Tetris: Chiado, Alfama e Madragoa. Os telespectadores poderiam sentar e contemplar a exposição que envolve-los a partir de três perspectivas. Consulte os títulos principais bairros históricos de Lisboa e as obras são folheadas em telhas de Azulejos artesanais concluídas com motivos e temas comuns na obra de azulejo português e Hispano-Moresque. Série de esculturas e pinturas de Tetris reflete perfeitamente a história portuguesa.', N'八面灵龙-玫瑰之美', 'Valkyrie Octopus-Rose', N'八面玲瓏-玫瑰之美', 'Valkyrie Octopus-Rose', N'乔安娜 • 瓦思康丝勒', 'Joana Vasconcelos', N'瓊安娜 • 瓦思康絲勒', 'Joana Vasconcelos', '2015', 'c18', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/mab/audio/mgm_color.mp3', '/com.buzz.exhibition/mab/audio/', 'mgm_color.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c19', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/mab/audio/mgm_flower.mp3', '/com.buzz.exhibition/mab/audio/', 'mgm_flower.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c20', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_dapao.mp3', '/com.buzz.exhibition/maa/audio/', 'music_dapao.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c21', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/dapao.png', '/com.buzz.exhibition/maa/images/', 'dapao.png', N'外国商船最早于澳门北湾作为停泊商港，其堤岸为半环形。到清同治年间，内港已成为北湾及浅湾的统称，乃环形港口。由于货运依赖水路，内港一带早已为繁荣地区。据《澳门图记》载述：“其西洋舶既入十字门者，又须由小十字门折而至南湾，又折而至娘妈角，然后抵澳。其水路至香山，须易小艇，夷舶不可到也。”由此看来，其天然水道浅窄。1868年，澳葡政府将北湾填塞，并筑成一道直堤小路，旁边筑有拱形铺楼。1923年，政府再填海扩阔沿堤马路和兴建码头，使内港成为现代化商港。[3]自中国实行改革开放后，内港发展为重要的海路交通枢纽，是连接香港及中国内地运输中心和渔业港口。', 'The former Yutong pier was part of the Ponte 16 project. The no. 14 pier was demolished and the new terminal at no. 11A was built to replace it. In order to reduce high speed ferries'' influence on the other harbour users, the Macau - Shekou and Macau - Jiangmen services were moved to Taipa Ferry Terminal from 24 September 2009 and 29 January 2010 respectively. Today the terminal only provide one cross-harbour service between Macau and Zhuhai.', N'外國商船最早於澳門北灣作為停泊商港，其隄岸為半環形。到清同治年間，內港已成為北灣及淺灣的統稱，乃環形港口。由於貨運依賴水路，內港一帶早已為繁榮地區。據《澳門圖記》載述：「其西洋舶既入十字門者，又須由小十字門折而至南灣，又折而至娘媽角，然後抵澳。其水路至香山，須易小艇，夷舶不可到也。」由此看來，其天然水道淺窄。1868年,澳葡政府將北灣填塞，並築成一道直隄小路，旁邊築有拱形鋪樓。1923年，政府再填海擴闊沿隄馬路和興建碼頭，使內港成為現代化商港。[3]自中國實行改革開放後，內港發展為重要的海路交通樞紐，是連接香港及中國內地運輸中心和漁業港口。', 'Macau desempenhou papel fundamental nas relações entre o Ocidente e a China por ter sido, até 1685, o único porto aberto por este império ao comércio exterior, o que transformou Macau, por mais de um século, no centro do tráfego marítimo e comercial e de intercâmbio de culturas entre o Ocidente e o Oriente.', N'澳门内港', 'The Inner Harbour,Macao', N'澳門內港Buzz', 'Porto Interior, Macau', N'佚名中国画家', 'UnidentifiedChinese', N'佚名中國畫家', 'Artista chinêsanónim', '1840', 'c22', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_mazu.mp3', '/com.buzz.exhibition/maa/audio/', 'music_mazu.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c23', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/mazu.png', '/com.buzz.exhibition/maa/images/', 'mazu.png', N'妈祖阁,主奉海神妈祖，是位于澳门半岛西南方的标志性建筑物之一，现为澳门三大古刹中最古老者。2005年作为澳门历史城区的部分被列入世界文化遗产名录内。\r\n\r\n妈阁庙始建于1488年，时值明朝。当时妈阁庙门前已为澳门之海岸线，是当地及附近居住之渔民作业之上岸补给、歇息和祈福之处。庙宇背山面海，沿崖建筑，古木参天，风光优美。整座庙宇包括大殿（又称正觉禅林）、石殿（又称神山第一殿）、弘仁殿、观音阁等4座主要建筑。石狮镇门、飞檐凌空，是一座富有中国文化特色的古建筑。\r\n\r\n经过庙门及花岗石牌坊，便是庭院，循着山麓的石阶小径，拾级而上，即可抵达建于巉岩巨石间、就石窟凿成的弘仁殿。殿内四壁，雕刻着海魔神将，色彩斑斓，中央供奉天后。自弘仁殿至观音阁，沿着山崖有不少石刻，或为名流政要咏题，或为骚人墨客遣兴，楷草篆隶，诸体俱备。观音阁位于庙之最高处，供奉观音菩萨。不少西洋画家亦曾描绘了庙前繁华景象，而妈阁庙亦出现在最早一批在中国拍摄的照片\r\n妈阁庙始建于1488年，时值明朝。当时妈阁庙门前已为澳门之海岸线，是当地及附近居住之渔民作业之上岸补给、歇息和祈福之处。庙宇背山面海，沿崖建筑，古木参天，风光优美。整座庙宇包括大殿（又称正觉禅林）、石殿（又称神山第一殿）、弘仁殿、观音阁等4座主要建筑。石狮镇门、飞檐凌空，是一座富有中国文化特色的古建筑。\r\n\r\n经过庙门及花岗石牌坊，便是庭院，循着山麓的石阶小径，拾级而上，即可抵达建于巉岩巨石间、就石窟凿成的弘仁殿。殿内四壁，雕刻着海魔神将，色彩斑斓，中央供奉天后。自弘仁殿至观音阁，沿着山崖有不少石刻，或为名流政要咏题，或为骚人墨客遣兴，楷草篆隶，诸体俱备。观音阁位于庙之最高处，供奉观音菩萨。不少西洋画家亦曾描绘了庙前繁华景象，而妈阁庙亦出现在最早一批在中国拍摄的照片', N'The name Macau is thought to be derived from the name of the temple. It is said that when the Portuguese sailors landed at the coast just outside the temple and asked the name of the place, the natives replied \"媽閣\" (Jyutping: \"Maa1 Gok3\") or \"A-Ma-Gau\" (bay of goddess A-Ma). The Portuguese then named the peninsula \"Macao\".[1] The temple was well described in ancient Chinese texts, as well as represented in paintings, related to Macao. It is also one of the first scenes photographed in Macao.\r\n\r\nTemple consists of six main parts:[2] Gate Pavilion, the Memorial Arch, the Prayer Hall, the Hall of Benevolence (the oldest part of the temple), the Hall of Guanyin, Zhengjiao Chanlin - Buddhist Pavilion.\r\n\r\nIn 2005, the temple became one of the designated sites of the Historic Centre of Macau enlisted on UNESCO World Heritage List.', N'媽祖閣,主奉海神媽祖，是位於澳門半島西南方的標誌性建築物之一，現為澳門三大古剎中最古老者。2005年作為澳門歷史城區的部分被列入世界文化遺產名錄內。\r\n\r\n媽閣廟始建於1488年，時值明朝。當時媽閣廟門前已為澳門之海岸線，是當地及附近居住之漁民作業之上岸補給、歇息和祈福之處。廟宇背山面海，沿崖建築，古木參天，風光優美。整座廟宇包括大殿（又稱正覺禪林）、石殿（又稱神山第一殿）、弘仁殿、觀音閣等4座主要建築。石獅鎮門、飛檐凌空，是一座富有中國文化特色的古建築。經過廟門及花崗石牌坊，便是庭院，循着山麓的石階小徑，拾級而上，即可抵達建於巉岩巨石間、就石窟鑿成的弘仁殿。殿內四壁，雕刻着海魔神將，色彩斑斕，中央供奉天后。自弘仁殿至觀音閣，沿着山崖有不少石刻，或為名流政要詠題，或為騷人墨客遣興，楷草篆隸，諸體俱備。觀音閣位於廟之最高處，供奉觀音菩薩。不少西洋畫家亦曾描繪了廟前繁華景象，而媽閣廟亦出現在最早一批在中國拍攝的照片。', 'Templo de A-Má, que se localiza à entrada do Porto Interior (no extremo-sul da Península de Macau), a meio da encosta poente da Colina da Barra, já existia antes da própria Cidade de Macau ter nascido. Especula-se que o templo foi construído pelos pescadores chineses residentes de Macau no séc. XV, para homenagear e adorar a Deusa A-Má (Deusa do Céu), chamada também de Tin Hau, Mazu ou Matsu. Esta divindade taoísta é muito venerada em todo o Sul da China e em várias partes do Sudeste Asiático e é considerada como a protectora dos pescadores e marinheiros. Crê-se que os portugueses desembarcaram pela primeira vez em Macau, possivelmente no ano de 1554 ou 1557, precisamente à entrada do Porto Interior, também chamada pelos pescadores chineses de \"Baía de A-Má\". Segundo as lendas do séc. XVI, o nome da Cidade deriva precisamente da palavra em cantonense \"A-Má-Gau\", que significa literalmente Baía de A-Má.', N'妈祖阁正殿前', 'Interior of the A-Ma Temple, Macao', N'媽祖閣正殿前', 'Interior do Templo deA-Má, Macau', N'奥古士丁·博尔杰', 'Auguste Borget', N'奧古士丁·博爾傑', 'Auguste Borget', '1842', 'c24', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/maa/images/macaudefault.png', '/com.buzz.exhibition/maa/images/', 'mac.png', N'“澳门艺术”把艺术展品带到物联纲世界, 令每一件展品发声!', '"Macau Arts" brings buzz to arts and cultural pieces, and it is one of the first Internet of Things application to the art and cultural world.', N'“澳門藝術”把藝術展品帶到物聯綱世界, 令每一件展品發聲!', '"Macau Arts" brings buzz to arts and cultural pieces, and it is one of the first Internet of Things application to the art and cultural world.', N'澳门艺术(测试用)', 'MacauArts(testing)', N'澳門藝術(測試用)', 'MacauArts(testing)', 'Buzz', 'Buzz', 'Buzz', 'Buzz', null, 'c25', '1', null);
INSERT INTO [dbo].[content] VALUES ('1', 'http://macauarts.buzz:90/download/maa/audio/music_mac.mp3', '/com.buzz.exhibition/maa/audio/', 'music_mac.mp3', null, null, null, null, null, null, null, null, null, null, null, null, null, 'c26', '1', null);
INSERT INTO [dbo].[content] VALUES ('2', 'http://macauarts.buzz:90/download/mad/images/old.jpg', '/com.buzz.exhibition/mad/images/', 'old.jpg', N'封面Capa!!!', null, null, null, null, null, null, null, null, null, null, null, null, 'c27', '1', null);
GO
COMMIT
GO

-- ----------------------------
--  Table structure for eventlist
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[eventlist]') AND type IN ('U'))
	DROP TABLE [dbo].[eventlist]
GO
CREATE TABLE [dbo].[eventlist] (
	[id] int IDENTITY(1,1) NOT NULL,
	[eventname] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[contentid] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[status] bit NOT NULL DEFAULT ((0)),
	[beaconmap] varchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[assign] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[executor] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[assigndate] smalldatetime NULL,
	[executedate] smalldatetime NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for exbeacon
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[exbeacon]') AND type IN ('U'))
	DROP TABLE [dbo].[exbeacon]
GO
CREATE TABLE [dbo].[exbeacon] (
	[extag] varchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[beaconid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of exbeacon
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-1');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-10');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-11');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-4');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-5');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-6');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-7');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-8');
INSERT INTO [dbo].[exbeacon] VALUES ('maa', '2397-9');
INSERT INTO [dbo].[exbeacon] VALUES ('mab', '2397-2');
INSERT INTO [dbo].[exbeacon] VALUES ('mab', '2397-3');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for excontent
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[excontent]') AND type IN ('U'))
	DROP TABLE [dbo].[excontent]
GO
CREATE TABLE [dbo].[excontent] (
	[extag] varchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[contentid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[usage] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of excontent
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c01', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c02', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c03', '0');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c04', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c05', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c06', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c07', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c08', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c09', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c10', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c11', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c12', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c13', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c14', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c21', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c22', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c23', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c24', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c25', '1');
INSERT INTO [dbo].[excontent] VALUES ('maa', 'c26', '1');
INSERT INTO [dbo].[excontent] VALUES ('mab', 'c16', '0');
INSERT INTO [dbo].[excontent] VALUES ('mab', 'c17', '1');
INSERT INTO [dbo].[excontent] VALUES ('mab', 'c18', '1');
INSERT INTO [dbo].[excontent] VALUES ('mab', 'c19', '1');
INSERT INTO [dbo].[excontent] VALUES ('mab', 'c20', '1');
INSERT INTO [dbo].[excontent] VALUES ('mac', 'c15', '0');
INSERT INTO [dbo].[excontent] VALUES ('mad', 'c27', '0');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for exmaster
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[exmaster]') AND type IN ('U'))
	DROP TABLE [dbo].[exmaster]
GO
CREATE TABLE [dbo].[exmaster] (
	[extag] varchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[title_cn] nvarchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[title_en] nvarchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[title_tw] nvarchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[title_pt] nvarchar(100) COLLATE Chinese_PRC_CI_AS NULL,
	[description_cn] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[description_en] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[description_tw] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[description_pt] nvarchar(1000) COLLATE Chinese_PRC_CI_AS NULL,
	[creator] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[datefrom] date NULL,
	[dateto] date NULL,
	[website] varchar(200) COLLATE Chinese_PRC_CI_AS NULL,
	[location] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[publish] uniqueidentifier NOT NULL DEFAULT (newid())
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of exmaster
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[exmaster] VALUES ('maa', N'澳门艺术(测试用)', 'MacauArts(testing)', N'澳門藝術(測試用)', 'MacauArts(testing)', N'澳门是欧洲艺术最早传入中国的地点，也是中国西洋画的发源地，以保存大量外销画和风俗画著称，其美术历程具有特殊性。 十八世纪后半叶至十九世纪中叶，不少西洋画家如英国钱纳利、法国博尔杰、苏格兰屈臣医生等来到澳门，描述了当时澳门及邻近地区的生活境况，与本地外销画匠共同创造了一个极具韵味的澳门美术时期。', 'During the 18th Century, a large number of the West European painters arrived at Chinese coastal cities due to intensive trade between China and the Mediterranean countries in Europe. To commemorate their visits, works produced by the western painters were largely about the life, customs and scenery of the places which they had visited. Some of their works were imitated and sold to foreign visitors as souvenirs and, therefore, spread overseas. These paintings provided vivid and clear visual information for the study of the history of Guangdong as well as the history of Chinese paintings in the late Qing Dynasty.', N'澳門是歐洲藝術最早傳入中國的地點，也是中國西洋畫的發源地，以保存大量外銷畫和風俗畫著稱，其美術歷程具有特殊性。十八世紀後半葉至十九世紀中葉，不少西洋畫家如英國錢納利、法國博爾傑、蘇格蘭屈臣醫生等來到澳門，描述了當時澳門及鄰近地區的生活境況，與本地外銷畫匠共同創造了一個極具韻味的澳門美術時期。简体中文', 'Durante o século XVIII, um grande número do Ocidente europeu pintores chegaram às cidades costeiras chinesas, devido ao intenso comércio entre a China e os países mediterrânicos na Europa. Para comemorar suas visitas, trabalhos produzidos pelos pintores ocidentais foram em grande parte sobre a vida, costumes e paisagens dos lugares que eles visitaram. Algumas das suas obras foram imitadas e vendeu para os visitantes estrangeiros como lembranças e, portanto, espalhar no exterior. Estas pinturas forneceram informações vívidas e visuais para o estudo da história de Guangdong, bem como a história da pintura chinesa no final da dinastia Qing.', 'nick.chung', '2015-01-01', '2016-01-01', null, null, '47B9E331-F733-4596-BE2E-498033510B75');
INSERT INTO [dbo].[exmaster] VALUES ('mab', N'澳门美高梅艺术装置展', 'Joana Vasconcelos at MGM MACAU', N'澳門美高梅藝術裝置展', 'Joana Vasconcelos at MGM MACAU', N'巨型艺术装置「八面灵龙」，灵感源自北欧神话，属于女武神（Valkyries）系列中的最新创作。穿梭于云雾间，她们从战场中挑选最英勇无畏的战士。「八面灵龙」呈现抽象的形态，四肢向外延绵伸展，宛如守护神一般，将整个天幕广场拥入怀中，承载着美好的祝愿。', 'Valkyrie Octopus is a monumental installation of the Valkyries series inspired by Norse mythology. These mythical female figures who flew over battlefields to select the bravest and most valorous warriors. Soaring over the space, this amorphous organic form embraces Grande Praça with her elongated tentacles, delivering blessings under her guardiance.', N'巨型藝術裝置「八面靈龍」，靈感源自北歐神話，屬於女武神（Valkyries）系列中的最新創作。穿梭於雲霧間，她們從戰場中挑選最英勇無畏的戰士。「八面靈龍」呈現抽象的形態，四肢向外延綿伸展，宛如守護神一般，將整個天幕廣場擁入懷中，承載着美好的祝願。', 'Valquíria polvo é uma instalação monumental da série Valquírias inspirado pela mitologia nórdica. Estas figuras femininas míticas que sobrevoaram o campo de batalha para selecionar os mais bravos e mais valorosos guerreiros. Planar sobre o espaço, esta forma orgânica amorfa abraça Grande Praça com seus tentáculos alongados, entregando bênçãos sob sua guardiance.', 'nick.chung', '2015-01-01', '2016-01-01', null, null, '4FFAB0EE-8A79-4C8C-BB01-645FFC329452');
INSERT INTO [dbo].[exmaster] VALUES ('mac', N'肖像油画', 'Portrait Oil Paintings', N'肖像油畫', 'Pinturas a Óleo Retrato', N'肖像艺术泛指人类对自身或特定个体形象所进行的诠释记录，涵盖立体、平面，如雕塑、绘画及影像等类型的创作，包括头像、胸像、半身像、全身像等。目前最早可追溯至公元前二百年左右的埃及彩绘石雕。', 'Portrait painting pertains to the genre of figure painting in the field of painting, and is traditionally referred to Chinese terms as xiezhao [capturing the appearance], chuanshen [conveying the spirits] or xiezhen [capturing the lifelikeness]. Prior to the invention of photography, it was a common and effective means of passing down the appearance, essence, energy and spirit of the ancestors to their offspring. ', N'肖像藝術泛指人類對自身或特定個體形象所進行的詮釋記錄，涵蓋立體、平面，如雕塑、繪畫及影像等類型的創作，包括頭像、胸像、半身像、全身像等。目前最早可追溯至公元前二百年左右的埃及彩繪石雕。', 'Pintura do retrato pertence ao gênero de pintura de figura no campo da pintura e é tradicionalmente chamada de termos chineses xiezhao [capturando a aparência], chuanshen [transportando os espíritos] ou xiezhen [captura a vida]. Antes da invenção da fotografia, era uma maneira comum e eficaz de passar para baixo a aparência, a essência, a energia e o espírito dos ancestrais para sua prole.', 'nick.chung', '2015-01-01', '2016-01-01', null, null, 'B737790A-EA96-4DCF-9549-66074A539961');
INSERT INTO [dbo].[exmaster] VALUES ('mad', N'落大雨水浸街－濠江旧影', 'Heavy Rain and Floods - Photographs of Macao', N'落大雨水浸街──濠江舊影', 'Chuvadas e Cheias - Fotografia de Macau', N'曾几何时，“落大雨，水浸街”是澳门常见情景，每逢雨季，频密地出现在澳门居民的眼帘。上世纪六、七十年代，在巴素打尔古街、十月初五街、新马路及新桥一带街巷，因位处海傍或低窪地区，一旦遇上暴雨、海水倒灌、台风，积水未能即时减退，顿成泽国，水深及膝，这样的画面，相信老居民都不会陌生。

苦与乐，是人生的双生儿，否则在中国人的字典里，就不会有“苦乐参半”这句话，更加不会有“苦中作乐”的人生态度。“落大雨，水浸街”，给居民造成诸多不便，不但阻碍出行，影响上班上学，经营商舖的，大都忙于清理积水、搬运货物，以免货物被沾湿破坏；但与此同时，豪雨造成的水淹让顽皮小童可以嬉水作乐，也算是一种不谙世事、大雨中的另类生活乐趣。苦与乐是相对的，身历其境的老居民一定有许多故事难以忘怀。

当年雨季一幕幕水浸街的情景，随著澳门城市建设的现代化，情况已大有改善，昔日水淹脚踝上学上班的情景，已经逐渐在居民的记忆中淡出。老街坊谈起昔日这些频繁水浸街的回忆时，也是个人及城市成长的共同话题。

感谢崔焕添先生为我们记录了这些特别的小城集体回忆，不吝惜地将这些珍贵的濠江旧影捐赠予澳门艺术博物馆。如果不是当年他冒著风雨涉水上街拍摄这些影像，昔日的生活片段就不会完整，我们的成长画面就会留下空白。', 'Until not long ago heavy rain frequently caused floods in Macao during rainy season. In the 1960s and 1970s, due to the seaside location and low altitude, when hit by storm, salt water intrusion or typhoon with heavy rains, areas including Rua do Visconde Paço de Arcos, Rua de Cinco de Outubro, Avenida de Almeida Ribeiro and San Kio area could easily flood, with water level reaching the knee. Older generation residents must have got used to those situations.', N'曾幾何時，“落大雨，水浸街”是澳門常見情景，每逢雨季，頻密地出現在澳門居民的眼簾。上世紀六、七十年代，在巴素打爾古街、十月初五街、新馬路及新橋一帶街巷，因位處海傍或低窪地區，一旦遇上暴雨、海水倒灌、颱風，積水未能即時減退，頓成澤國，水深及膝，這樣的畫面，相信老居民都不會陌生。

苦與樂，是人生的雙生兒，否則在中國人的字典裡，就不會有“苦樂參半”這句話，更加不會有“苦中作樂”的人生態度。“落大雨，水浸街”，給居民造成諸多不便，不但阻礙出行，影響上班上學，經營商舖的，大都忙於清理積水、搬運貨物，以免貨物被沾濕破壞；但與此同時，豪雨造成的水淹讓頑皮小童可以嬉水作樂，也算是一種不諳世事、大雨中的另類生活樂趣。苦與樂是相對的，身歷其境的老居民一定有許多故事難以忘懷。

當年雨季一幕幕水浸街的情景，隨著澳門城市建設的現代化，情況已大有改善，昔日水淹腳踝上學上班的情景，已經逐漸在居民的記憶中淡出。老街坊談起昔日這些頻繁水浸街的回憶時，也是個人及城市成長的共同話題。', 'Até há relativamente pouco tempo, as fortes chuvadas de estação causavam frequentes inundações em Macau. Nas décadas de 1960 e 1970, devido à sua localização ribeirinha, quando havia tempestades, infiltração de água salgada ou a forte pluviosidade típica dos tufões, as zonas da Rua do Visconde Paço de Arcos, Rua de Cinco de Outubro, Avenida de Almeida Ribeiro (San Ma Lou) e o bairro San Kiu ficavam rapidamente inundadas, com o nível das águas a chegar aos joelhos. As gerações mais idosas de residentes tiveram de se habituar a estas situações.', 'stuart', '2015-01-01', '2016-12-01', null, null, 'A9616BA4-D223-4ACF-B8A9-6862AF13B8B6');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for orgmaster
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[orgmaster]') AND type IN ('U'))
	DROP TABLE [dbo].[orgmaster]
GO
CREATE TABLE [dbo].[orgmaster] (
	[orgid] int IDENTITY(1,1) NOT NULL,
	[orgname] nvarchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[porgid] int NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for sysconfig
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[sysconfig]') AND type IN ('U'))
	DROP TABLE [dbo].[sysconfig]
GO
CREATE TABLE [dbo].[sysconfig] (
	[id] int IDENTITY(1,1) NOT NULL,
	[ballviewrange] int NOT NULL,
	[ballsizestep] int NOT NULL,
	[extag] varchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of sysconfig
-- ----------------------------
BEGIN TRANSACTION
GO
SET IDENTITY_INSERT [dbo].[sysconfig] ON
GO
INSERT INTO [dbo].[sysconfig] ([id], [ballviewrange], [ballsizestep], [extag]) VALUES ('1', '-90', '-5', 'maa');
GO
SET IDENTITY_INSERT [dbo].[sysconfig] OFF
GO
COMMIT
GO

-- ----------------------------
--  Table structure for syslog
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[syslog]') AND type IN ('U'))
	DROP TABLE [dbo].[syslog]
GO
CREATE TABLE [dbo].[syslog] (
	[userid] varchar(10) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[beaconid] varchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[logtime] datetime2(7) NOT NULL,
	[triggertype] tinyint NOT NULL,
	[id] bigint IDENTITY(1,1) NOT NULL,
	[extag] varchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for sysparams
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[sysparams]') AND type IN ('U'))
	DROP TABLE [dbo].[sysparams]
GO
CREATE TABLE [dbo].[sysparams] (
	[id] int IDENTITY(1,1) NOT NULL,
	[paramgroup] varchar(20) COLLATE Chinese_PRC_CI_AS NULL,
	[paramkey] tinyint NULL,
	[paramvalue] varchar(20) COLLATE Chinese_PRC_CI_AS NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of sysparams
-- ----------------------------
BEGIN TRANSACTION
GO
SET IDENTITY_INSERT [dbo].[sysparams] ON
GO
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('1', 'contenttype', '0', 'video');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('2', 'contenttype', '1', 'audio');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('3', 'contenttype', '2', 'image');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('4', 'triggertype', '0', 'in');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('5', 'triggertype', '1', 'out');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('6', 'triggertype', '2', 'stay');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('7', 'triggertype', '3', 'through');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('8', 'voicelang', '0', 'cc');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('9', 'voicelang', '1', 'sc');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('10', 'voicelang', '2', 'en');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('11', 'voicelang', '3', 'pt');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('12', 'beaconusage', '0', 'introduction');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('13', 'beaconusage', '1', 'detail');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('14', 'rangedirection', '0', 'front');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('15', 'rangedirection', '1', 'back');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('16', 'rangedirection', '2', 'both');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('17', 'displaylang', '0', 'tw');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('18', 'displaylang', '1', 'cn');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('19', 'displaylang', '2', 'en');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('20', 'displaylang', '3', 'pt');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('21', 'contentusage', '0', 'catalog');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('22', 'contentusage', '1', 'paint');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('23', 'contentrange', '0', 'immediate');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('24', 'contentrange', '1', 'near');
INSERT INTO [dbo].[sysparams] ([id], [paramgroup], [paramkey], [paramvalue]) VALUES ('25', 'contentrange', '2', 'far');
GO
SET IDENTITY_INSERT [dbo].[sysparams] OFF
GO
COMMIT
GO

-- ----------------------------
--  Table structure for sysrole
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[sysrole]') AND type IN ('U'))
	DROP TABLE [dbo].[sysrole]
GO
CREATE TABLE [dbo].[sysrole] (
	[roleid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[rolename] nvarchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for sysuser
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[sysuser]') AND type IN ('U'))
	DROP TABLE [dbo].[sysuser]
GO
CREATE TABLE [dbo].[sysuser] (
	[userid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[password] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[nickname] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[orgid] int NULL,
	[email] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of sysuser
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[sysuser] VALUES ('aaa', 'ccc', 'bbb', '0', null);
INSERT INTO [dbo].[sysuser] VALUES ('adsf', 'asdf', 'asdf', '0', null);
INSERT INTO [dbo].[sysuser] VALUES ('nick.li', '145236', 'Nick Li', '0', null);
INSERT INTO [dbo].[sysuser] VALUES ('sam.chen', '123456', 'Sam Chen', '0', null);
INSERT INTO [dbo].[sysuser] VALUES ('samuelchan', 'samsam', 'sam', '0', null);
INSERT INTO [dbo].[sysuser] VALUES ('test1', 'test1', 'test1', '0', null);
INSERT INTO [dbo].[sysuser] VALUES ('tony.wu', '147258369', 'Tony Wu', '0', null);
GO
COMMIT
GO

-- ----------------------------
--  Table structure for trigger
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[trigger]') AND type IN ('U'))
	DROP TABLE [dbo].[trigger]
GO
CREATE TABLE [dbo].[trigger] (
	[triggertype] tinyint NOT NULL,
	[triggercount] tinyint NULL,
	[triggerfrequency] int NULL,
	[triggerid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of trigger
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [dbo].[trigger] VALUES ('0', '1', '500', 't01');
INSERT INTO [dbo].[trigger] VALUES ('1', '1', '500', 't02');
INSERT INTO [dbo].[trigger] VALUES ('2', '5', '500', 't03');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for userrole
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[userrole]') AND type IN ('U'))
	DROP TABLE [dbo].[userrole]
GO
CREATE TABLE [dbo].[userrole] (
	[userid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[roleid] varchar(20) COLLATE Chinese_PRC_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  View structure for [dbo].[v_view]
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[v_view]') AND type IN ('V'))
	DROP VIEW [dbo].[v_view]
GO
CREATE VIEW [dbo].[v_view] AS SELECT
	a.extag,
	a.title_cn,
	a.title_en,
	a.title_tw,
	a.description_cn,
	a.description_en,
	a.description_tw,
	b. USAGE,
	c.serverpath
FROM
	exmaster a
LEFT JOIN excontent b ON a.extag = b.extag
LEFT JOIN content c ON b.contentid = c.contentid
WHERE
	usage = 0

GO

-- ----------------------------
--  Procedure structure for [dbo].sp_wz
-- ----------------------------
CREATE PROCEDURE [dbo].[sp_wz]  
as 
begin
	UPDATE content set serverpath=replace(serverpath,'http://120.24.85.165/','http://macauarts.buzz:90/')
end

GO

-- ----------------------------
--  Procedure structure for [dbo].sp_al
-- ----------------------------
CREATE PROCEDURE [dbo].[sp_al] 
as 
begin
	UPDATE content set serverpath=replace(serverpath,'http://macauarts.buzz:90/','http://120.24.85.165/')
end

GO

-- ----------------------------
--  Procedure structure for [dbo].sp_newid
-- ----------------------------
CREATE PROCEDURE [dbo].[sp_newid] 
as 
begin
	UPDATE appversion SET publishedversion=NEWID() WHERE id=1
end

GO


-- ----------------------------
--  Primary key structure for table appuser
-- ----------------------------
ALTER TABLE [dbo].[appuser] ADD
	CONSTRAINT [PK__appuser__CBA1B2572E1BDC42] PRIMARY KEY CLUSTERED ([userid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table appversion
-- ----------------------------
ALTER TABLE [dbo].[appversion] ADD
	CONSTRAINT [PK__version__3213E83F5FB337D6] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table beacon
-- ----------------------------
ALTER TABLE [dbo].[beacon] ADD
	CONSTRAINT [PK__beacon__BB57D3B9440B1D61] PRIMARY KEY CLUSTERED ([beaconid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table beaconaction
-- ----------------------------
ALTER TABLE [dbo].[beaconaction] ADD
	CONSTRAINT [PK__beaconac__6A79D23446E78A0C] PRIMARY KEY CLUSTERED ([beaconid],[triggerid],[contentid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table beaconuuid
-- ----------------------------
ALTER TABLE [dbo].[beaconuuid] ADD
	CONSTRAINT [PK__beaconuu__3213E83F6383C8BA] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table content
-- ----------------------------
ALTER TABLE [dbo].[content] ADD
	CONSTRAINT [PK__content__0BDD8B3130F848ED] PRIMARY KEY CLUSTERED ([contentid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table eventlist
-- ----------------------------
ALTER TABLE [dbo].[eventlist] ADD
	CONSTRAINT [PK__eventlis__3213E83F6E01572D] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table exbeacon
-- ----------------------------
ALTER TABLE [dbo].[exbeacon] ADD
	CONSTRAINT [PK__exbeacon__2F7F7EC84CA06362] PRIMARY KEY CLUSTERED ([extag],[beaconid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table excontent
-- ----------------------------
ALTER TABLE [dbo].[excontent] ADD
	CONSTRAINT [PK__exconten__A477DB4049C3F6B7] PRIMARY KEY CLUSTERED ([extag],[contentid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table exmaster
-- ----------------------------
ALTER TABLE [dbo].[exmaster] ADD
	CONSTRAINT [PK__exmaster__A4CA03F30EA330E9] PRIMARY KEY CLUSTERED ([extag]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table orgmaster
-- ----------------------------
ALTER TABLE [dbo].[orgmaster] ADD
	CONSTRAINT [PK__orgmaste__3580E7B03D5E1FD2] PRIMARY KEY CLUSTERED ([orgid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table sysconfig
-- ----------------------------
ALTER TABLE [dbo].[sysconfig] ADD
	CONSTRAINT [PK__sysconfi__3213E83F72C60C4A] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table syslog
-- ----------------------------
ALTER TABLE [dbo].[syslog] ADD
	CONSTRAINT [PK__syslog__3213E83F33D4B598] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table sysrole
-- ----------------------------
ALTER TABLE [dbo].[sysrole] ADD
	CONSTRAINT [PK__sysrole__CD994BF22B3F6F97] PRIMARY KEY CLUSTERED ([roleid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table sysuser
-- ----------------------------
ALTER TABLE [dbo].[sysuser] ADD
	CONSTRAINT [PK__sysuser__CBA1B257398D8EEE] PRIMARY KEY CLUSTERED ([userid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table trigger
-- ----------------------------
ALTER TABLE [dbo].[trigger] ADD
	CONSTRAINT [PK__trigger__125DC06F36B12243] PRIMARY KEY CLUSTERED ([triggerid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table userrole
-- ----------------------------
ALTER TABLE [dbo].[userrole] ADD
	CONSTRAINT [PK__userrole__F77826E84F7CD00D] PRIMARY KEY CLUSTERED ([userid],[roleid]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Options for table appuser
-- ----------------------------
ALTER TABLE [dbo].[appuser] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table appversion
-- ----------------------------
ALTER TABLE [dbo].[appversion] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[dbo].[appversion]', RESEED, 4)
GO

-- ----------------------------
--  Options for table beacon
-- ----------------------------
ALTER TABLE [dbo].[beacon] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table beaconaction
-- ----------------------------
ALTER TABLE [dbo].[beaconaction] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table beaconuuid
-- ----------------------------
ALTER TABLE [dbo].[beaconuuid] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[dbo].[beaconuuid]', RESEED, 4)
GO

-- ----------------------------
--  Options for table content
-- ----------------------------
ALTER TABLE [dbo].[content] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table eventlist
-- ----------------------------
ALTER TABLE [dbo].[eventlist] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[dbo].[eventlist]', RESEED, 1)
GO

-- ----------------------------
--  Options for table exbeacon
-- ----------------------------
ALTER TABLE [dbo].[exbeacon] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table excontent
-- ----------------------------
ALTER TABLE [dbo].[excontent] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table exmaster
-- ----------------------------
ALTER TABLE [dbo].[exmaster] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table orgmaster
-- ----------------------------
ALTER TABLE [dbo].[orgmaster] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[dbo].[orgmaster]', RESEED, 1)
GO

-- ----------------------------
--  Options for table sysconfig
-- ----------------------------
ALTER TABLE [dbo].[sysconfig] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[dbo].[sysconfig]', RESEED, 1)
GO

-- ----------------------------
--  Options for table syslog
-- ----------------------------
ALTER TABLE [dbo].[syslog] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[dbo].[syslog]', RESEED, 4123)
GO

-- ----------------------------
--  Options for table sysparams
-- ----------------------------
ALTER TABLE [dbo].[sysparams] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[dbo].[sysparams]', RESEED, 25)
GO

-- ----------------------------
--  Options for table sysrole
-- ----------------------------
ALTER TABLE [dbo].[sysrole] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table sysuser
-- ----------------------------
ALTER TABLE [dbo].[sysuser] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table trigger
-- ----------------------------
ALTER TABLE [dbo].[trigger] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table userrole
-- ----------------------------
ALTER TABLE [dbo].[userrole] SET (LOCK_ESCALATION = TABLE)
GO

