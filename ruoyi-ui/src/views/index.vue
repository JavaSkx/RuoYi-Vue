<template>
  <div class="app-container home">

    <h1 style="color: cornflowerblue" id="">公告通知！！！</h1>
    <h2 style="color: lightslategray ">	<!--2023年3月22日是第三十一届“世界水日”，3月22—28日是第三十六届“中国水周”。联合国确定2023年“世界水日”主题为“Accelerating Change”（加速变革）。
      为深入贯彻党的二十大精神，贯彻落实习近平生态文明思想、习近平法治思想和习近平总书记治水重要论述精神，推动《中华人民共和国长江保护法》《中华人民共和国黄河保护法》等法律法规有效实施，水利部办公厅日前印发通知，确定我国纪念2023年“世界水日”“中国水周”活动主题为“强化依法治水 携手共护母亲河”，并公布2023年“世界水日”“中国水周”活动方案和宣传口号。
      根据安排，“世界水日”“中国水周”期间，水利部将组团参加2023年联合国水大会。同时，水利部将举办第二届寻找“最美家乡河”活动揭晓仪式；开展黄河保护法宣贯专题活动；发布主题宣传画和宣传口号；组织开展长江保护法、黄河保护法志愿宣传活动；在“学习强国”平台开展专项答题，在水利部网站等平台开辟宣贯专栏，开展网络答题活动；举办第三届全国节约用水知识大赛、“节水中国 你我同行”主题宣传联合行动；
      开展《关于加强新时代水土保持工作的意见》宣贯工作等，为进一步增强全社会依法治水、惜水护水意识，推动新阶段水利高质量发展营造良好氛围、提供有力保障。
    -->
      <div style="width: 1271px; height: 351px;" v-html="form.noticeContent"></div>
    </h2>
  </div>
</template>

<script>
import {getNotice} from "@/api/system/notice";
export default {
  data() {
    return {
      // 表单参数
      form: {},
      id:1
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      getNotice(this.id).then(response => {
        this.form = response.data;
      });
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  line-height: 100px;
  text-align: center;

}
</style>

