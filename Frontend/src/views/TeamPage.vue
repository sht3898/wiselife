<template>
  <v-container style="padding-top:10px">
    <v-flex lg10 xs12 class="ma-auto">
      <h2 class="headline mb-3">
        About
        <strong>Service</strong>
      </h2>
      <br />
      <p class="infotext" style="font-size:14pt; text-align:center">
        슬기로운 여가생활은
        <br />
        <span style="color:darkorange">' 개인의 성향에 맞춘 여가생활 추천 서비스 '</span>를
        <br />제공합니다!
      </p>
      <br />

      <v-flex class="pb-12">
        <p class="infocontent">
          사용자의 성별, 지역, 나이대, 관심사, 성향을 통해 빅데이터 분석을 하여 사용자에게 맞는 활동을 추천해주고 실천할 수 있도록 강좌와 모임을 소개하여 슬기로운 여가생활을 누릴 수 있게 해줍니다.
          <br />
          <br />1) 무료한 현대 사회인들을 위해 빅데이터를 활용하여 개인의 성향과 관심사에 맞춘 여가생활 추천
          <br />
          <br />2) 이용자들의 성향과 관심사에 맞춘 지역별 강좌와 모임을 소개하여 함께 즐길 수 있는 슬기로운 여가생활을 독려
          <br />
          <br />3) 여가생활의 활성화로 현대 사회인의 스트레스 완화와 지역별 소통의 기회를 넓히고 건전한 여가생활을 통해 지역 주민들의 자기개발과 다양한 프로그램 제공
          <br />
          <br />이를 통해, 정신적, 육체적 건강을 유지, 향상하여 삶의 질을 높이고 자신의 잠재적 재능을 확인하고 표현함으로써 자아실현의 기회를 가질 수 있게 해줍니다.
        </p>
      </v-flex>

      <h2 class="headline mb-3">
        About
        <strong>Developers</strong>
      </h2>
      <br />

      <v-expansion-panels popout class="mb-12">
        <v-expansion-panel hide-actions>
          <v-expansion-panel-header>
            <v-row align="center" class="spacer" no-gutters>
              <v-col cols="4" sm="2" md="1">
                <v-avatar size="36px">
                  <img alt="Avatar" :src="getImgUrl('김수빈.jpg')" />
                </v-avatar>
              </v-col>

              <v-col class="hidden-xs-only" sm="5" md="3">
                <v-chip
                  :color="`blue  lighten-3`"
                  class="ml-0 mr-6 black--text"
                  label
                  small
                >Back-end</v-chip>
                <strong>김수빈</strong>
              </v-col>

              <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                <span>팀장</span>
              </v-col>

              <v-col class="grey--text text-truncate">soobin1080@naver.com</v-col>
            </v-row>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-divider></v-divider>
            <v-card-text>
              <v-row>
                <v-col cols="4">Rest API 구축</v-col>
                <v-col>사용자 관련 및 채팅 API 구축</v-col>
              </v-row>
              <v-row>
                <v-col cols="4">BigData</v-col>
                <v-col>강좌/모임 데이터 가공</v-col>
              </v-row>
            </v-card-text>
          </v-expansion-panel-content>
        </v-expansion-panel>

        <v-expansion-panel hide-actions>
          <v-expansion-panel-header>
            <v-row align="center" class="spacer" no-gutters>
              <v-col cols="4" sm="2" md="1">
                <v-avatar size="36px">
                  <img alt="Avatar" :src="getImgUrl('김가은.jpg')" />
                </v-avatar>
              </v-col>

              <v-col class="hidden-xs-only" sm="5" md="3">
                <v-chip :color="`blue lighten-3`" class="ml-0 mr-6 black--text" label small>Back-end</v-chip>
                <strong>김가은</strong>
              </v-col>

              <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                <span>코딩 깡패</span>
              </v-col>

              <v-col class="grey--text text-truncate">l9960204@naver.com</v-col>
            </v-row>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-divider></v-divider>
            <v-card-text>
              <v-row>
                <v-col cols="4">Rest API 구축</v-col>
                <v-col>강좌/모임 컨텐츠 관련 API 구축</v-col>
              </v-row>
              <v-row>
                <v-col cols="4">BigData</v-col>
                <v-col>강좌/모임 데이터 가공</v-col>
              </v-row>
            </v-card-text>
          </v-expansion-panel-content>
        </v-expansion-panel>

        <v-expansion-panel hide-actions>
          <v-expansion-panel-header>
            <v-row align="center" class="spacer" no-gutters>
              <v-col cols="4" sm="2" md="1">
                <v-avatar size="36px">
                  <img alt="Avatar" :src="getImgUrl('서현택.jpg')" />
                </v-avatar>
              </v-col>

              <v-col class="hidden-xs-only" sm="5" md="3">
                <v-chip :color="`blue lighten-3`" class="ml-0 mr-6 black--text" label small>Back-end</v-chip>
                <strong>서현택</strong>
              </v-col>

              <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                <span>빅데이터</span>
              </v-col>

              <v-col class="grey--text text-truncate">sehyunteg@naver.com</v-col>
            </v-row>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-divider></v-divider>
            <v-card-text>
              <v-row>
                <v-col cols="4">DB 마이그레이션</v-col>
                <v-col>데이터 수집 및 마이그레이션</v-col>
              </v-row>
              <v-row>
                <v-col cols="4">BigData</v-col>
                <v-col>데이터 가공 및 Item-based CF 추천 시스템 구현</v-col>
              </v-row>
            </v-card-text>
          </v-expansion-panel-content>
        </v-expansion-panel>

        <v-expansion-panel hide-actions>
          <v-expansion-panel-header>
            <v-row align="center" class="spacer" no-gutters>
              <v-col cols="4" sm="2" md="1">
                <v-avatar size="36px">
                  <img alt="Avatar" :src="getImgUrl('김대래.jpg')" />
                </v-avatar>
              </v-col>

              <v-col class="hidden-xs-only" sm="5" md="3">
                <v-chip
                  :color="`orange lighten-3`"
                  class="ml-0 mr-6 black--text"
                  label
                  small
                >Front-end</v-chip>
                <strong>김대래</strong>
              </v-col>

              <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                <span>디버거</span>
              </v-col>

              <v-col class="grey--text text-truncate">drk0830@naver.com</v-col>
            </v-row>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-divider></v-divider>
            <v-card-text>
              <v-row>
                <v-col cols="4">DataBase 설계 및 BigData</v-col>
                <v-col>ERD 설계 및 추천 시스템 고도화</v-col>
              </v-row>
              <v-row>
                <v-col cols="4">화면 구현 및 API 활용</v-col>
                <v-col>사용자 및 성향 검사 화면 구현</v-col>
              </v-row>
            </v-card-text>
          </v-expansion-panel-content>
        </v-expansion-panel>

        <v-expansion-panel hide-actions>
          <v-expansion-panel-header>
            <v-row align="center" class="spacer" no-gutters>
              <v-col cols="4" sm="2" md="1">
                <v-avatar size="36px">
                  <img alt="Avatar" :src="getImgUrl('안지연.jpg')" />
                </v-avatar>
              </v-col>

              <v-col class="hidden-xs-only" sm="5" md="3">
                <v-chip
                  :color="`orange lighten-3`"
                  class="ml-0 mr-6 black--text"
                  label
                  small
                >Front-end</v-chip>
                <strong>안지연</strong>
              </v-col>

              <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                <span>프론트 및 배포</span>
              </v-col>

              <v-col class="grey--text text-truncate">jennyahn94@naver.com</v-col>
            </v-row>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-divider></v-divider>
            <v-card-text>
              <v-row>
                <v-col cols="4">화면 구현 및 API 활용</v-col>
                <v-col>강좌/모임 컨텐츠 화면 구현</v-col>
              </v-row>
              <v-row>
                <v-col cols="4">AWS 배포</v-col>
                <v-col>서버 배포 (Nginx, Gunicorn)</v-col>
              </v-row>
            </v-card-text>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-flex>
  </v-container>
</template>

<script>
export default {
  name: "TeamPage",
  components: {},
  methods: {
    getImgUrl(img) {
      return require("../assets/members/" + img);
    }
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap");
.text-no-wrap {
  font-size: 15pt;
  font-family: "Nanum Pen Script", cursive;
}

@import url("https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap");
.infotext {
  font-family: "Black Han Sans", sans-serif;
  font-size: 23px;
}

@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic+Coding:wght@700&display=swap");
.infocontent {
  font-family: "Nanum Gothic Coding", monospace;
}
</style>
