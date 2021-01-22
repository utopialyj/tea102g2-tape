(function ($) {
    var city_list = [
        { pinyin: '台北市', name: '台北市' }, { pinyin: '新北市', name: '新北市' }, { pinyin: '基隆市', name: '基隆市' },
        { pinyin: '桃園縣', name: '桃園縣' }, { pinyin: '新竹縣', name: '新竹縣' }, { pinyin: '新竹市', name: '新竹市' },
        { pinyin: '苗栗縣', name: '苗栗縣' }, { pinyin: '宜蘭縣', name: '宜蘭縣' }, { pinyin: '台中市', name: '台中市' },
        { pinyin: '彰化縣', name: '彰化縣' }, { pinyin: '南投縣', name: '南投縣' }, { pinyin: '雲林縣', name: '雲林縣' },
        { pinyin: '嘉義市', name: '嘉義市' }, { pinyin: '嘉義縣', name: '嘉義縣' }, { pinyin: '台南市', name: '台南市' },
        { pinyin: '高雄市', name: '高雄市' }, { pinyin: '屏東縣', name: '屏東縣' }, { pinyin: '花蓮縣', name: '花蓮縣' },
        { pinyin: '台東縣', name: '台東縣' }, { pinyin: '澎湖縣', name: '澎湖縣' }, { pinyin: '金門縣', name: '金門縣' },
        { pinyin: '連江縣', name: '連江縣' }
        ]
    var dist_list = [
        { city: '基隆市', pinyin: '七堵區', name: '七堵區' },
        { city: '澎湖縣', pinyin: '七美鄉', name: '七美鄉' },
        { city: '台南市', pinyin: '七股區', name: '七股區' },
        { city: '屏東縣', pinyin: '三地門鄉', name: '三地門鄉' },
        { city: '新北市', pinyin: '三峽區', name: '三峽區' },
        { city: '宜蘭縣', pinyin: '三星鄉', name: '三星鄉' },
        { city: '高雄市', pinyin: '三民區', name: '三民區' },
        { city: '苗栗縣', pinyin: '三灣鄉', name: '三灣鄉' },
        { city: '苗栗縣', pinyin: '三義鄉', name: '三義鄉' },
        { city: '新北市', pinyin: '三芝區', name: '三芝區' },
        { city: '新北市', pinyin: '三重區', name: '三重區' },
        { city: '台南市', pinyin: '下營區', name: '下營區' },
        { city: '台中市', pinyin: '中區', name: '中區' },
        { city: '新北市', pinyin: '中和區', name: '中和區' },
        { city: '嘉義縣', pinyin: '中埔鄉', name: '中埔鄉' },
        { city: '桃園縣', pinyin: '中壢市', name: '中壢市' },
        { city: '南投縣', pinyin: '中寮鄉', name: '中寮鄉' },
        { city: '基隆市', pinyin: '中山區', name: '中山區' },
        { city: '台北市', pinyin: '中山區', name: '中山區' },
        { city: '基隆市', pinyin: '中正區', name: '中正區' },
        { city: '台北市', pinyin: '中正區', name: '中正區' },
        { city: '台南市', pinyin: '中西區', name: '中西區' },
        { city: '屏東縣', pinyin: '九如鄉', name: '九如鄉' },
        { city: '雲林縣', pinyin: '二崙鄉', name: '二崙鄉' },
        { city: '彰化縣', pinyin: '二林鎮', name: '二林鎮' },
        { city: '彰化縣', pinyin: '二水鄉', name: '二水鄉' },
        { city: '新竹縣', pinyin: '五峰鄉', name: '五峰鄉' },
        { city: '宜蘭縣', pinyin: '五結鄉', name: '五結鄉' },
        { city: '新北市', pinyin: '五股區', name: '五股區' },
        { city: '台南市', pinyin: '仁德區', name: '仁德區' },
        { city: '基隆市', pinyin: '仁愛區', name: '仁愛區' },
        { city: '南投縣', pinyin: '仁愛鄉', name: '仁愛鄉' },
        { city: '高雄市', pinyin: '三民區', name: '三民區' },
        { city: '彰化縣', pinyin: '伸港鄉', name: '伸港鄉' },
        { city: '屏東縣', pinyin: '佳冬鄉', name: '佳冬鄉' },
        { city: '台南市', pinyin: '佳里區', name: '佳里區' },
        { city: '屏東縣', pinyin: '來義鄉', name: '來義鄉' },
        { city: '基隆市', pinyin: '信義區', name: '信義區' },
        { city: '台北市', pinyin: '信義區', name: '信義區' },
        { city: '南投縣', pinyin: '信義鄉', name: '信義鄉' },
        { city: '雲林縣', pinyin: '元長鄉', name: '元長鄉' },
        { city: '花蓮縣', pinyin: '光復鄉', name: '光復鄉' },
        { city: '屏東縣', pinyin: '內埔鄉', name: '內埔鄉' },
        { city: '台北市', pinyin: '內湖區', name: '內湖區' },
        { city: '高雄市', pinyin: '內門區', name: '內門區' },
        { city: '桃園縣', pinyin: '八德市', name: '八德市' },
        { city: '新北市', pinyin: '八里區', name: '八里區' },
        { city: '苗栗縣', pinyin: '公館鄉', name: '公館鄉' },
        { city: '台南市', pinyin: '六甲區', name: '六甲區' },
        { city: '嘉義縣', pinyin: '六腳鄉', name: '六腳鄉' },
        { city: '高雄市', pinyin: '六龜區', name: '六龜區' },
        { city: '宜蘭縣', pinyin: '冬山鄉', name: '冬山鄉' },
        { city: '高雄市', pinyin: '前金區', name: '前金區' },
        { city: '高雄市', pinyin: '前鎮區', name: '前鎮區' },
        { city: '新竹市', pinyin: '北區', name: '北區' },
        { city: '台中市', pinyin: '北區', name: '北區' },
        { city: '台南市', pinyin: '北區', name: '北區' },
        { city: '新竹縣', pinyin: '北埔鄉', name: '北埔鄉' },
        { city: '台中市', pinyin: '北屯區', name: '北屯區' },
        { city: '台北市', pinyin: '北投區', name: '北投區' },
        { city: '彰化縣', pinyin: '北斗鎮', name: '北斗鎮' },
        { city: '雲林縣', pinyin: '北港鎮', name: '北港鎮' },
        { city: '連江縣', pinyin: '北竿鄉', name: '北竿鄉' },
        { city: '台南市', pinyin: '北門區', name: '北門區' },
        { city: '台東縣', pinyin: '卑南鄉', name: '卑南鄉' },
        { city: '花蓮縣', pinyin: '卓溪鄉', name: '卓溪鄉' },
        { city: '苗栗縣', pinyin: '卓蘭鎮', name: '卓蘭鎮' },
        { city: '台南市', pinyin: '南化區', name: '南化區' },
        { city: '台中市', pinyin: '南區', name: '南區' },
        { city: '台南市', pinyin: '南區', name: '南區' },
        { city: '台中市', pinyin: '南屯區', name: '南屯區' },
        { city: '屏東縣', pinyin: '南州鄉', name: '南州鄉' },
        { city: '苗栗縣', pinyin: '南庄鄉', name: '南庄鄉' },
        { city: '南投縣', pinyin: '南投市', name: '南投市' },
        { city: '台北市', pinyin: '南港區', name: '南港區' },
        { city: '宜蘭縣', pinyin: '南澳鄉', name: '南澳鄉' },
        { city: '連江縣', pinyin: '南竿鄉', name: '南竿鄉' },
        { city: '雲林縣', pinyin: '口湖鄉', name: '口湖鄉' },
        { city: '雲林縣', pinyin: '古坑鄉', name: '古坑鄉' },
        { city: '台東縣', pinyin: '台東市', name: '台東市' },
        { city: '雲林縣', pinyin: '台西鄉', name: '台西鄉' },
        { city: '花蓮縣', pinyin: '吉安鄉', name: '吉安鄉' },
        { city: '南投縣', pinyin: '名間鄉', name: '名間鄉' },
        { city: '台中市', pinyin: '后里區', name: '后里區' },
        { city: '台中市', pinyin: '和平區', name: '和平區' },
        { city: '彰化縣', pinyin: '和美鎮', name: '和美鎮' },
        { city: '宜蘭縣', pinyin: '員山鄉', name: '員山鄉' },
        { city: '彰化縣', pinyin: '員林鎮', name: '員林鎮' },
        { city: '台南市', pinyin: '善化區', name: '善化區' },
        { city: '雲林縣', pinyin: '四湖鄉', name: '四湖鄉' },
        { city: '南投縣', pinyin: '國姓鄉', name: '國姓鄉' },
        { city: '新北市', pinyin: '土城區', name: '土城區' },
        { city: '雲林縣', pinyin: '土庫鎮', name: '土庫鎮' },
        { city: '新北市', pinyin: '坪林區', name: '坪林區' },
        { city: '彰化縣', pinyin: '埔心鄉', name: '埔心鄉' },
        { city: '南投縣', pinyin: '埔里鎮', name: '埔里鎮' },
        { city: '彰化縣', pinyin: '埔鹽鄉', name: '埔鹽鄉' },
        { city: '彰化縣', pinyin: '埤頭鄉', name: '埤頭鄉' },
        { city: '台北市', pinyin: '士林區', name: '士林區' },
        { city: '宜蘭縣', pinyin: '壯圍鄉', name: '壯圍鄉' },
        { city: '花蓮縣', pinyin: '壽豐鄉', name: '壽豐鄉' },
        { city: '台中市', pinyin: '外埔區', name: '外埔區' },
        { city: '台南市', pinyin: '大內區', name: '大內區' },
        { city: '台北市', pinyin: '大同區', name: '大同區' },
        { city: '宜蘭縣', pinyin: '大同鄉', name: '大同鄉' },
        { city: '桃園縣', pinyin: '大園鄉', name: '大園鄉' },
        { city: '彰化縣', pinyin: '大城鄉', name: '大城鄉' },
        { city: '嘉義縣', pinyin: '大埔鄉', name: '大埔鄉' },
        { city: '雲林縣', pinyin: '大埤鄉', name: '大埤鄉' },
        { city: '台中市', pinyin: '大安區', name: '大安區' },
        { city: '台北市', pinyin: '大安區', name: '大安區' },
        { city: '高雄市', pinyin: '大寮區', name: '大寮區' },
        { city: '彰化縣', pinyin: '大村鄉', name: '大村鄉' },
        { city: '嘉義縣', pinyin: '大林鎮', name: '大林鎮' },
        { city: '高雄市', pinyin: '大樹區', name: '大樹區' },
        { city: '台東縣', pinyin: '大武鄉', name: '大武鄉' },
        { city: '苗栗縣', pinyin: '大湖鄉', name: '大湖鄉' },
        { city: '桃園縣', pinyin: '大溪鎮', name: '大溪鎮' },
        { city: '台中市', pinyin: '大甲區', name: '大甲區' },
        { city: '高雄市', pinyin: '大社區', name: '大社區' },
        { city: '台中市', pinyin: '大肚區', name: '大肚區' },
        { city: '台中市', pinyin: '大里區', name: '大里區' },
        { city: '台中市', pinyin: '大雅區', name: '大雅區' },
        { city: '嘉義縣', pinyin: '太保市', name: '太保市' },
        { city: '台中市', pinyin: '太平區', name: '太平區' },
        { city: '台東縣', pinyin: '太麻里鄉', name: '太麻里鄉' },
        { city: '台南市', pinyin: '學甲區', name: '學甲區' },
        { city: '台南市', pinyin: '安南區', name: '安南區' },
        { city: '台南市', pinyin: '安定區', name: '安定區' },
        { city: '台南市', pinyin: '安平區', name: '安平區' },
        { city: '基隆市', pinyin: '安樂區', name: '安樂區' },
        { city: '台南市', pinyin: '官田區', name: '官田區' },
        { city: '宜蘭縣', pinyin: '宜蘭市', name: '宜蘭市' },
        { city: '花蓮縣', pinyin: '富里鄉', name: '富里鄉' },
        { city: '新竹縣', pinyin: '寶山鄉', name: '寶山鄉' },
        { city: '台南市', pinyin: '將軍區', name: '將軍區' },
        { city: '高雄市', pinyin: '小港區', name: '小港區' },
        { city: '新竹縣', pinyin: '尖石鄉', name: '尖石鄉' },
        { city: '屏東縣', pinyin: '屏東市', name: '屏東市' },
        { city: '台南市', pinyin: '山上區', name: '山上區' },
        { city: '高雄市', pinyin: '岡山區', name: '岡山區' },
        { city: '新竹縣', pinyin: '峨眉鄉', name: '峨眉鄉' },
        { city: '屏東縣', pinyin: '崁頂鄉', name: '崁頂鄉' },
        { city: '雲林縣', pinyin: '崙背鄉', name: '崙背鄉' },
        { city: '高雄市', pinyin: '左營區', name: '左營區' },
        { city: '台南市', pinyin: '左鎮區', name: '左鎮區' },
        { city: '嘉義縣', pinyin: '布袋鎮', name: '布袋鎮' },
        { city: '新北市', pinyin: '平溪區', name: '平溪區' },
        { city: '桃園縣', pinyin: '平鎮市', name: '平鎮市' },
        { city: '台東縣', pinyin: '延平鄉', name: '延平鄉' },
        { city: '高雄市', pinyin: '彌陀區', name: '彌陀區' },
        { city: '彰化縣', pinyin: '彰化市', name: '彰化市' },
        { city: '台南市', pinyin: '後壁區', name: '後壁區' },
        { city: '苗栗縣', pinyin: '後龍鎮', name: '後龍鎮' },
        { city: '桃園縣', pinyin: '復興鄉', name: '復興鄉' },
        { city: '屏東縣', pinyin: '恆春鎮', name: '恆春鎮' },
        { city: '台東縣', pinyin: '成功鎮', name: '成功鎮' },
        { city: '台北市', pinyin: '文山區', name: '文山區' },
        { city: '雲林縣', pinyin: '斗六市', name: '斗六市' },
        { city: '雲林縣', pinyin: '斗南鎮', name: '斗南鎮' },
        { city: '台南市', pinyin: '新化區', name: '新化區' },
        { city: '屏東縣', pinyin: '新園鄉', name: '新園鄉' },
        { city: '花蓮縣', pinyin: '新城鄉', name: '新城鄉' },
        { city: '新竹縣', pinyin: '新埔鎮', name: '新埔鎮' },
        { city: '屏東縣', pinyin: '新埤鄉', name: '新埤鄉' },
        { city: '桃園縣', pinyin: '新屋鄉', name: '新屋鄉' },
        { city: '台南市', pinyin: '新市區', name: '新市區' },
        { city: '新北市', pinyin: '新店區', name: '新店區' },
        { city: '嘉義縣', pinyin: '新港鄉', name: '新港鄉' },
        { city: '台南市', pinyin: '新營區', name: '新營區' },
        { city: '台中市', pinyin: '新社區', name: '新社區' },
        { city: '高雄市', pinyin: '新興區', name: '新興區' },
        { city: '新北市', pinyin: '新莊區', name: '新莊區' },
        { city: '新竹縣', pinyin: '新豐鄉', name: '新豐鄉' },
        { city: '高雄市', pinyin: '旗山區', name: '旗山區' },
        { city: '高雄市', pinyin: '旗津區', name: '旗津區' },
        { city: '屏東縣', pinyin: '春日鄉', name: '春日鄉' },
        { city: '基隆市', pinyin: '暖暖區', name: '暖暖區' },
        { city: '澎湖縣', pinyin: '望安鄉', name: '望安鄉' },
        { city: '嘉義縣', pinyin: '朴子市', name: '朴子市' },
        { city: '高雄市', pinyin: '杉林區', name: '杉林區' },
        { city: '台中市', pinyin: '東勢區', name: '東勢區' },
        { city: '雲林縣', pinyin: '東勢鄉', name: '東勢鄉' },
        { city: '嘉義市', pinyin: '東區', name: '東區' },
        { city: '新竹市', pinyin: '東區', name: '東區' },
        { city: '台中市', pinyin: '東區', name: '東區' },
        { city: '台南市', pinyin: '東區', name: '東區' },
        { city: '台南市', pinyin: '東山區', name: '東山區' },
        { city: '連江縣', pinyin: '東引鄉', name: '東引鄉' },
        { city: '台東縣', pinyin: '東河鄉', name: '東河鄉' },
        { city: '屏東縣', pinyin: '東港鎮', name: '東港鎮' },
        { city: '嘉義縣', pinyin: '東石鄉', name: '東石鄉' },
        { city: '台北市', pinyin: '松山區', name: '松山區' },
        { city: '新北市', pinyin: '板橋區', name: '板橋區' },
        { city: '屏東縣', pinyin: '枋寮鄉', name: '枋寮鄉' },
        { city: '屏東縣', pinyin: '枋山鄉', name: '枋山鄉' },
        { city: '雲林縣', pinyin: '林內鄉', name: '林內鄉' },
        { city: '新北市', pinyin: '林口區', name: '林口區' },
        { city: '高雄市', pinyin: '林園區', name: '林園區' },
        { city: '屏東縣', pinyin: '林邊鄉', name: '林邊鄉' },
        { city: '台南市', pinyin: '柳營區', name: '柳營區' },
        { city: '桃園縣', pinyin: '桃園市', name: '桃園市' },
        { city: '高雄市', pinyin: '桃源區', name: '桃源區' },
        { city: '嘉義縣', pinyin: '梅山鄉', name: '梅山鄉' },
        { city: '高雄市', pinyin: '梓官區', name: '梓官區' },
        { city: '台中市', pinyin: '梧棲區', name: '梧棲區' },
        { city: '桃園縣', pinyin: '楊梅市', name: '楊梅市' },
        { city: '高雄市', pinyin: '楠梓區', name: '楠梓區' },
        { city: '台南市', pinyin: '楠西區', name: '楠西區' },
        { city: '新北市', pinyin: '樹林區', name: '樹林區' },
        { city: '高雄市', pinyin: '橋頭區', name: '橋頭區' },
        { city: '新竹縣', pinyin: '橫山鄉', name: '橫山鄉' },
        { city: '台南市', pinyin: '歸仁區', name: '歸仁區' },
        { city: '嘉義縣', pinyin: '民雄鄉', name: '民雄鄉' },
        { city: '嘉義縣', pinyin: '水上鄉', name: '水上鄉' },
        { city: '雲林縣', pinyin: '水林鄉', name: '水林鄉' },
        { city: '南投縣', pinyin: '水里鄉', name: '水里鄉' },
        { city: '新北市', pinyin: '永和區', name: '永和區' },
        { city: '高雄市', pinyin: '永安區', name: '永安區' },
        { city: '台南市', pinyin: '永康區', name: '永康區' },
        { city: '彰化縣', pinyin: '永靖鄉', name: '永靖鄉' },
        { city: '新北市', pinyin: '汐止區', name: '汐止區' },
        { city: '台東縣', pinyin: '池上鄉', name: '池上鄉' },
        { city: '台中市', pinyin: '沙鹿區', name: '沙鹿區' },
        { city: '苗栗縣', pinyin: '泰安鄉', name: '泰安鄉' },
        { city: '新北市', pinyin: '泰山區', name: '泰山區' },
        { city: '屏東縣', pinyin: '泰武鄉', name: '泰武鄉' },
        { city: '台東縣', pinyin: '海端鄉', name: '海端鄉' },
        { city: '新北市', pinyin: '淡水區', name: '淡水區' },
        { city: '新北市', pinyin: '深坑區', name: '深坑區' },
        { city: '台中市', pinyin: '清水區', name: '清水區' },
        { city: '高雄市', pinyin: '湖內區', name: '湖內區' },
        { city: '新竹縣', pinyin: '湖口鄉', name: '湖口鄉' },
        { city: '澎湖縣', pinyin: '湖西鄉', name: '湖西鄉' },
        { city: '嘉義縣', pinyin: '溪口鄉', name: '溪口鄉' },
        { city: '彰化縣', pinyin: '溪州鄉', name: '溪州鄉' },
        { city: '彰化縣', pinyin: '溪湖鎮', name: '溪湖鎮' },
        { city: '屏東縣', pinyin: '滿州鄉', name: '滿州鄉' },
        { city: '台中市', pinyin: '潭子區', name: '潭子區' },
        { city: '屏東縣', pinyin: '潮州鎮', name: '潮州鎮' },
        { city: '金門縣', pinyin: '烈嶼鄉', name: '烈嶼鄉' },
        { city: '新北市', pinyin: '烏來區', name: '烏來區' },
        { city: '金門縣', pinyin: '烏坵鄉', name: '烏坵鄉' },
        { city: '台中市', pinyin: '烏日區', name: '烏日區' },
        { city: '高雄市', pinyin: '燕巢區', name: '燕巢區' },
        { city: '屏東縣', pinyin: '牡丹鄉', name: '牡丹鄉' },
        { city: '屏東縣', pinyin: '獅子鄉', name: '獅子鄉' },
        { city: '苗栗縣', pinyin: '獅潭鄉', name: '獅潭鄉' },
        { city: '台南市', pinyin: '玉井區', name: '玉井區' },
        { city: '花蓮縣', pinyin: '玉里鎮', name: '玉里鎮' },
        { city: '屏東縣', pinyin: '琉球鄉', name: '琉球鄉' },
        { city: '花蓮縣', pinyin: '瑞穗鄉', name: '瑞穗鄉' },
        { city: '新北市', pinyin: '瑞芳區', name: '瑞芳區' },
        { city: '屏東縣', pinyin: '瑪家鄉', name: '瑪家鄉' },
        { city: '彰化縣', pinyin: '田中鎮', name: '田中鎮' },
        { city: '高雄市', pinyin: '田寮區', name: '田寮區' },
        { city: '彰化縣', pinyin: '田尾鄉', name: '田尾鄉' },
        { city: '高雄市', pinyin: '甲仙區', name: '甲仙區' },
        { city: '嘉義縣', pinyin: '番路鄉', name: '番路鄉' },
        { city: '澎湖縣', pinyin: '白沙鄉', name: '白沙鄉' },
        { city: '台南市', pinyin: '白河區', name: '白河區' },
        { city: '台中市', pinyin: '石岡區', name: '石岡區' },
        { city: '新北市', pinyin: '石碇區', name: '石碇區' },
        { city: '新北市', pinyin: '石門區', name: '石門區' },
        { city: '宜蘭縣', pinyin: '礁溪鄉', name: '礁溪鄉' },
        { city: '彰化縣', pinyin: '社頭鄉', name: '社頭鄉' },
        { city: '台中市', pinyin: '神岡區', name: '神岡區' },
        { city: '彰化縣', pinyin: '福興鄉', name: '福興鄉' },
        { city: '花蓮縣', pinyin: '秀林鄉', name: '秀林鄉' },
        { city: '彰化縣', pinyin: '秀水鄉', name: '秀水鄉' },
        { city: '新竹縣', pinyin: '竹北市', name: '竹北市' },
        { city: '苗栗縣', pinyin: '竹南鎮', name: '竹南鎮' },
        { city: '彰化縣', pinyin: '竹塘鄉', name: '竹塘鄉' },
        { city: '南投縣', pinyin: '竹山鎮', name: '竹山鎮' },
        { city: '嘉義縣', pinyin: '竹崎鄉', name: '竹崎鄉' },
        { city: '新竹縣', pinyin: '竹東鎮', name: '竹東鎮' },
        { city: '屏東縣', pinyin: '竹田鄉', name: '竹田鄉' },
        { city: '台東縣', pinyin: '綠島鄉', name: '綠島鄉' },
        { city: '彰化縣', pinyin: '線西鄉', name: '線西鄉' },
        { city: '宜蘭縣', pinyin: '羅東鎮', name: '羅東鎮' },
        { city: '高雄市', pinyin: '美濃區', name: '美濃區' },
        { city: '嘉義縣', pinyin: '義竹鄉', name: '義竹鄉' },
        { city: '新竹縣', pinyin: '芎林鄉', name: '芎林鄉' },
        { city: '彰化縣', pinyin: '芬園鄉', name: '芬園鄉' },
        { city: '彰化縣', pinyin: '花壇鄉', name: '花壇鄉' },
        { city: '花蓮縣', pinyin: '花蓮市', name: '花蓮市' },
        { city: '彰化縣', pinyin: '芳苑鄉', name: '芳苑鄉' },
        { city: '苗栗縣', pinyin: '苑裡鎮', name: '苑裡鎮' },
        { city: '高雄市', pinyin: '苓雅區', name: '苓雅區' },
        { city: '苗栗縣', pinyin: '苗栗市', name: '苗栗市' },
        { city: '高雄市', pinyin: '茂林區', name: '茂林區' },
        { city: '高雄市', pinyin: '茄萣區', name: '茄萣區' },
        { city: '南投縣', pinyin: '草屯鎮', name: '草屯鎮' },
        { city: '連江縣', pinyin: '莒光鄉', name: '莒光鄉' },
        { city: '雲林縣', pinyin: '莿桐鄉', name: '莿桐鄉' },
        { city: '屏東縣', pinyin: '萬丹鄉', name: '萬丹鄉' },
        { city: '屏東縣', pinyin: '萬巒鄉', name: '萬巒鄉' },
        { city: '花蓮縣', pinyin: '萬榮鄉', name: '萬榮鄉' },
        { city: '台北市', pinyin: '萬華區', name: '萬華區' },
        { city: '新北市', pinyin: '萬里區', name: '萬里區' },
        { city: '新北市', pinyin: '蘆洲區', name: '蘆洲區' },
        { city: '桃園縣', pinyin: '蘆竹鄉', name: '蘆竹鄉' },
        { city: '宜蘭縣', pinyin: '蘇澳鎮', name: '蘇澳鎮' },
        { city: '台東縣', pinyin: '蘭嶼鄉', name: '蘭嶼鄉' },
        { city: '雲林縣', pinyin: '虎尾鎮', name: '虎尾鎮' },
        { city: '雲林縣', pinyin: '褒忠鄉', name: '褒忠鄉' },
        { city: '嘉義市', pinyin: '西區', name: '西區' },
        { city: '台中市', pinyin: '西區', name: '西區' },
        { city: '台中市', pinyin: '西屯區', name: '西屯區' },
        { city: '澎湖縣', pinyin: '西嶼鄉', name: '西嶼鄉' },
        { city: '台南市', pinyin: '西港區', name: '西港區' },
        { city: '苗栗縣', pinyin: '西湖鄉', name: '西湖鄉' },
        { city: '雲林縣', pinyin: '西螺鎮', name: '西螺鎮' },
        { city: '桃園縣', pinyin: '觀音鄉', name: '觀音鄉' },
        { city: '台中市', pinyin: '豐原區', name: '豐原區' },
        { city: '花蓮縣', pinyin: '豐濱鄉', name: '豐濱鄉' },
        { city: '新北市', pinyin: '貢寮區', name: '貢寮區' },
        { city: '高雄市', pinyin: '路竹區', name: '路竹區' },
        { city: '屏東縣', pinyin: '車城鄉', name: '車城鄉' },
        { city: '苗栗縣', pinyin: '通霄鎮', name: '通霄鎮' },
        { city: '苗栗縣', pinyin: '造橋鄉', name: '造橋鄉' },
        { city: '台東縣', pinyin: '達仁鄉', name: '達仁鄉' },
        { city: '高雄市', pinyin: '那瑪夏區', name: '那瑪夏區' },
        { city: '屏東縣', pinyin: '里港鄉', name: '里港鄉' },
        { city: '金門縣', pinyin: '金城鎮', name: '金城鎮' },
        { city: '金門縣', pinyin: '金寧鄉', name: '金寧鄉' },
        { city: '新北市', pinyin: '金山區', name: '金山區' },
        { city: '台東縣', pinyin: '金峰鄉', name: '金峰鄉' },
        { city: '金門縣', pinyin: '金沙鎮', name: '金沙鎮' },
        { city: '金門縣', pinyin: '金湖鎮', name: '金湖鎮' },
        { city: '苗栗縣', pinyin: '銅鑼鄉', name: '銅鑼鄉' },
        { city: '屏東縣', pinyin: '長治鄉', name: '長治鄉' },
        { city: '台東縣', pinyin: '長濱鄉', name: '長濱鄉' },
        { city: '台東縣', pinyin: '關山鎮', name: '關山鎮' },
        { city: '台南市', pinyin: '關廟區', name: '關廟區' },
        { city: '新竹縣', pinyin: '關西鎮', name: '關西鎮' },
        { city: '高雄市', pinyin: '阿蓮區', name: '阿蓮區' },
        { city: '嘉義縣', pinyin: '阿里山鄉', name: '阿里山鄉' },
        { city: '南投縣', pinyin: '集集鎮', name: '集集鎮' },
        { city: '新北市', pinyin: '雙溪區', name: '雙溪區' },
        { city: '屏東縣', pinyin: '霧台鄉', name: '霧台鄉' },
        { city: '台中市', pinyin: '霧峰區', name: '霧峰區' },
        { city: '苗栗縣', pinyin: '頭份鎮', name: '頭份鎮' },
        { city: '宜蘭縣', pinyin: '頭城鎮', name: '頭城鎮' },
        { city: '苗栗縣', pinyin: '頭屋鄉', name: '頭屋鄉' },
        { city: '新竹市', pinyin: '香山區', name: '香山區' },
        { city: '澎湖縣', pinyin: '馬公市', name: '馬公市' },
        { city: '屏東縣', pinyin: '高樹鄉', name: '高樹鄉' },
        { city: '南投縣', pinyin: '魚池鄉', name: '魚池鄉' },
        { city: '高雄市', pinyin: '鳥松區', name: '鳥松區' },
        { city: '高雄市', pinyin: '鳳山區', name: '鳳山區' },
        { city: '花蓮縣', pinyin: '鳳林鎮', name: '鳳林鎮' },
        { city: '新北市', pinyin: '鶯歌區', name: '鶯歌區' },
        { city: '屏東縣', pinyin: '鹽埔鄉', name: '鹽埔鄉' },
        { city: '高雄市', pinyin: '鹽埕區', name: '鹽埕區' },
        { city: '台南市', pinyin: '鹽水區', name: '鹽水區' },
        { city: '彰化縣', pinyin: '鹿港鎮', name: '鹿港鎮' },
        { city: '嘉義縣', pinyin: '鹿草鄉', name: '鹿草鄉' },
        { city: '南投縣', pinyin: '鹿谷鄉', name: '鹿谷鄉' },
        { city: '台東縣', pinyin: '鹿野鄉', name: '鹿野鄉' },
        { city: '屏東縣', pinyin: '麟洛鄉', name: '麟洛鄉' },
        { city: '雲林縣', pinyin: '麥寮鄉', name: '麥寮鄉' },
        { city: '台南市', pinyin: '麻豆區', name: '麻豆區' },
        { city: '高雄市', pinyin: '鼓山區', name: '鼓山區' },
        { city: '台中市', pinyin: '龍井區', name: '龍井區' },
        { city: '台南市', pinyin: '龍崎區', name: '龍崎區' },
        { city: '桃園縣', pinyin: '龍潭鄉', name: '龍潭鄉' },
        { city: '桃園縣', pinyin: '龜山鄉', name: '龜山鄉' }
        ];

    $.fn.twaddress = function () {
        this.each(function () {
            function info() {
                this.city = '';
                this.city_pinyin = '';
                this.dist = '';
                this.dist_pinyin = '';
                this.detail = '';
            }
            var o = new info();
            var old_dist = null;
            var opts = $.extend({}, $.fn.twaddress.defaults);
            var options = { city: "", dist: "", detail: "" };
            var fisttimeflag = true;
            var $inputbox = $(this);
            var $twaddrs = $('<div style="display: inline;"></div>');
            var $twcity = $('<select id="cities" name="cities"></select>');
            var $twmore = $('<input type="text" class="input" id="addresss" name="addresss"></input>');
            for (var i = 0; i < city_list.length; i++) {
                if ($inputbox.val().indexOf(city_list[i].name) >= 0) {
                    options.city = city_list[i].name;
                    $inputbox.val($inputbox.val().replace(city_list[i].name, ""));
                }
            }
            var mx_len = 0;
            for (var i = 0; i < dist_list.length; i++) {
                if ($inputbox.val().indexOf(dist_list[i].name) >= 0) {
                    if (mx_len < dist_list[i].name.length) {
                        mx_len = dist_list[i].name.length;
                        options.dist = dist_list[i].name;
                    }
                }
            }
            $inputbox.val($inputbox.val().replace(options.dist, ""));
            options.detail = $inputbox.val();
            $twcity.keyup(function () {
                $twcity.change();
            });
            $twcity.change(function () {
                var $new_twdist = $('<select id="areas" name="areas"></select>');
                if (options != undefined && options.detail != undefined && fisttimeflag) {
                    if (options.detail != "hidden") {
                        $twmore.val(options.detail);
                    } else {
                        $twmore.hide();
                    }
                }
                for (var i = 0; i < dist_list.length; i++) {
                    if (dist_list[i].city == $twcity.val()) {
                        if (options != undefined && options.dist != undefined && fisttimeflag && dist_list[i].name == options.dist) {
                            $new_twdist.append('<option value="' + dist_list[i].pinyin + '" selected="selected">' + dist_list[i].name + '</option>');
                            fisttimeflag = false;
                        } else {
                            $new_twdist.append('<option value="' + dist_list[i].pinyin + '">' + dist_list[i].name + '</option>');
                        }
                    }
                }
                $twcity.after($new_twdist);
                $inputbox.val(getValue($twcity, $new_twdist, $twmore, o));
                $inputbox.change();

                $new_twdist.keyup(function () {
                    $new_twdist.change();
                });
                $new_twdist.change(function () {
                    $inputbox.val(getValue($twcity, $new_twdist, $twmore, o));
                    $inputbox.change();
                });
                $twmore.change(function () {
                    $inputbox.val(getValue($twcity, $new_twdist, $twmore, o));
                    $inputbox.change();
                });
                if (old_dist != null) {
                    old_dist.hide().remove();
                }
                old_dist = $new_twdist;
            });

            for (var i = 0; i < city_list.length; i++) {
                if (options != undefined && options.city != undefined && city_list[i].name == options.city) {
                    $twcity.append('<option value="' + city_list[i].pinyin + '" selected="selected">' + city_list[i].name + '</option>');
                } else {
                    $twcity.append('<option value="' + city_list[i].pinyin + '">' + city_list[i].name + '</option>');
                }
            }
            $twaddrs.append($twcity);
            $twaddrs.append($twmore);
            $(this).after($twaddrs);
            $(this).hide();
            $twcity.change();
            return o;
        });
        function getValue(city, dist, more, o) {
            var full_address = '';
            for (var i = 0; i < city_list.length; i++) {
                if (city_list[i].pinyin == city.val()) {
                    full_address = city_list[i].name;
                    o.city = city_list[i].name;
                }
            }
            for (var i = 0; i < dist_list.length; i++) {
                if (dist_list[i].city == city.val() && dist_list[i].pinyin == dist.val()) {
                    full_address += dist_list[i].name;
                    o.dist = dist_list[i].name;
                }
            }
            o.city_pinyin = city.val();
            o.dist_pinyin = dist.val();
            o.detail = more.val();
            return full_address + more.val();
        }
    };

})(jQuery);

$(function () {
    $(".twaddress").twaddress();
});