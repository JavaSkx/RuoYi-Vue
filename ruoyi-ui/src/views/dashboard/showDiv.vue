<template>
    <div class="showDivMain">
      <div class="titleDiv">
        <span class="titleSpan" :style="{'--color': color}">{{ title }}</span>
      </div>
      <div class="textDiv">
        <span class="textSpan" :style="{'--color': textColor}">{{ showText() }}</span>
        <span v-if="unit" class="unitSpan" :style="{'--color': unitColor}">{{ showUnit() }}</span>
        <!-- <el-button v-if="toView" type="text" @click="toViewMethod()">{{ $t('查看') }}</el-button> -->
      </div>
    </div>
  </template>
  
  <script>
    import { keepDecimal } from '@/utils'
    export default {
      name: 'ShowDiv',
      props: {
        title: {
          type: String,
          required: true
        },
        color: {
          type: String,
          default: '#666666'
        },
        textColor: {
          type: String,
          default: '#333333'
        },
        unitColor: {
          type: String,
          default: '#737373'
        },
        text: {
          type: Number,
          required: true
        },
        unit: {
          type: String,
          default: ''
        },
        autoUnit: {
          type: Boolean,
          default: false
        },
        toView: {
          type: Boolean,
          default: false
        }
      },
      methods: {
        showText() {
          if(this.autoUnit && this.text) {
  
            const numberLength = this.text.toString().length
            if (numberLength>=9) {
              const showText = this.text / 100000000
              return keepDecimal(showText, 1)
            } else if (numberLength>=5) {
              const showText = this.text / 10000
              return keepDecimal(showText, 1)
            } else {
              return this.text
            }
          } else {
            return this.text
          }
        },
        showUnit() {
          if(this.autoUnit) {
            const prefixUnit = this.getPrefixUnit()
            return prefixUnit + this.unit
          } else {
            return this.unit
          }
        },
        getPrefixUnit() {
          // 统计text位数，转为对应的万，亿单位
          if(!this.text) {
            return ''
          }
          const numberLength = this.text.toString().length
          if (numberLength>=9) {
            return '亿'
          } else if (numberLength>=5) {
            return '万'
          } else {
            return ''
          }
        }
     
      }
    }
  </script>
  
  <style scoped>
    .titleSpan {
      font-family: "PingFang SC";
      font-weight: 600;
      font-size: 12px;
      color: #9e9e9e;
      letter-spacing: 0;
      line-height: 22px;
    }
  
    .unitSpan {
      font-size: 12px;
      color: var(--color);
      height: 22px;
      font-family: "PingFang SC";
      font-weight: 500;
      font-size: 12px;
      letter-spacing: 0;
      line-height: 22px;
      margin-top: 10px;
      margin-left: 5px;
    }
    .textSpan {
      font-family: "PingFang SC";
      font-weight: 500;
      font-size: 28px;
      color: var(--color);
      letter-spacing: 0;
    }
  
    .showDivMain {
      display: flex;
      flex-direction: column;
      align-items: center;
      height: 100%;
      width: 100%;
      padding-bottom: 10px;
    }
  
    .titleDiv {
      height: 32px;
      display: flex;
      align-items: center;
    }
    .textDiv {
      flex: 1;
      display: flex;
      align-items: center;
    }
  </style>
  