<template>
  <q-dialog
      ref="dialog"
      @hide="onDialogHide"
      persistent
      autofocus
      transition-show="slide-up"
      transition-hide="slide-down"
  >
    <q-card class="q-dialog-plugin" style="width: 800px">
      <q-bar v-if="mode === 'ins'" class="text-white bg-primary">
        <div>{{ txt_lang("newRecord") }}</div>
      </q-bar>
      <q-bar v-if="mode === 'upd'" class="text-white bg-primary">
        <div>{{ txt_lang("editRecord") }}</div>
      </q-bar>

      <q-card>
        <q-tabs v-model="tab" class="text-teal">
          <q-tab name="main" :label="txt_lang('mainProp')"/>
          <q-tab name="dop" :label="txt_lang('dopProp')"/>
        </q-tabs>

        <q-separator/>

        <q-tab-panels
            v-model="tab"
            animated
            swipeable
            vertical
            transition-prev="jump-up"
            transition-next="jump-up"
        >
          <q-tab-panel name="main">
            <!-- name -->
            <q-input
                autofocus
                :dense="dense"
                :model-value="form.name"
                v-model="form.name"
                @blur="onBlurName"
                :label="txt_lang('fldName')"
                :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
            >
            </q-input>
            <!-- fullName-->
            <q-input
                :dense="dense"
                :model-value="form.fullName"
                v-model="form.fullName"
                :label="txt_lang('fldFullName')"
                :rules="[(val) => (!!val && !!val.trim()) || txt_lang('req')]"
            >
            </q-input>

            <!-- cod -->
            <q-input
                :dense="dense"
                v-model="form.cod"
                :model-value="form.cod"
                :label="txt_lang('code')"
                :placeholder="txt_lang('msgCodeGen')"
            />
            <!-- meterStruct -->
            <q-select
                :dense="dense"
                v-model="meterStruct"
                :model-value="meterStruct"
                :options="optionsStruct"
                :label="txt_lang('meterStruct')"
                option-value="id"
                option-label="text"
                map-options
                @update:model-value="fnSelectDict('meterStruct')"
            />

            <!-- Measure -->
            <q-select
                :dense="dense"
                v-model="measure"
                :options="measures"
                :label="txt_lang('measure')"
                option-value="id"
                option-label="name"
                map-options
                :model-value="measure"
                @update:model-value="fnSelMeasure"
            />

            <!-- accessLevel -->
            <q-select
                :dense="dense"
                v-model="al"
                :options="optionsLevel"
                :label="txt_lang('accessLevel')"
                option-value="id"
                option-label="text"
                map-options
                :model-value="al"
                @update:model-value="fnSelectDict('accessLevel')"
            />

            <!-- cmt -->
            <q-input
                :dense="dense"
                :model-value="form.cmt"
                v-model="form.cmt"
                type="textarea"
                :label="txt_lang('fldCmt')"
            >
            </q-input>
          </q-tab-panel>

          <q-tab-panel name="dop">
            <!-- meter deterministic -->
            <q-select
                :dense="dense"
                v-model="meterDeterm"
                :model-value="meterDeterm"
                :options="optionsDeterm"
                :label="txt_lang('meterDeterm')"
                option-value="id"
                option-label="text"
                map-options
                @update:model-value="fnSelectDict('meterDeterm')"
            />
            <!-- distributionLaw -->
            <q-select
                :dense="dense"
                v-model="distributionLaw"
                :model-value="distributionLaw"
                :options="optionsDistribution"
                :label="txt_lang('distributionLaw')"
                option-value="id"
                option-label="text"
                map-options
                @update:model-value="fnSelectDict('distribution')"
            />

            <!-- meterTypeByRate -->
            <q-select
                :dense="dense"
                v-model="meterTypeByRate"
                :model-value="meterTypeByRate"
                :options="optionsByRate"
                :label="txt_lang('meterTypeByRate')"
                option-value="id"
                option-label="text"
                map-options
                @update:model-value="fnSelectDict('meterTypeByRate')"
            />
            <!-- meterTypeByPeriod -->
            <q-select
                :dense="dense"
                v-model="meterTypeByPeriod"
                :model-value="meterTypeByPeriod"
                :options="optionsByPeriod"
                :label="txt_lang('meterTypeByPeriod')"
                option-value="id"
                option-label="text"
                map-options
                @update:model-value="fnSelectDict('meterTypeByPeriod')"
            />
            <!-- meterTypeByMember -->
            <q-select
                :dense="dense"
                v-model="meterTypeByMember"
                :model-value="meterTypeByMember"
                :options="optionsByMember"
                :label="txt_lang('meterTypeByMember')"
                option-value="id"
                option-label="text"
                map-options
                @update:model-value="fnSelectDict('meterTypeByMember')"
            />
            <!-- meterBehavior -->
            <q-select
                :dense="dense"
                v-model="meterBehavior"
                :model-value="meterBehavior"
                :options="optionsBehavior"
                :label="txt_lang('meterBehavior')"
                option-value="id"
                option-label="text"
                map-options
                @update:model-value="fnSelectDict('meterBehavior')"
            />

            <q-input
                v-model="form.minVal"
                :model-value="form.minVal"
                :dense="dense"
                :label="txt_lang('minVal')"
            />
            <q-input
                v-model="form.maxVal"
                :model-value="form.maxVal"
                :dense="dense"
                :label="txt_lang('maxVal')"
            />
          </q-tab-panel>
        </q-tab-panels>
      </q-card>

      <q-card-actions align="right">
        <q-btn
            :dense="dense"
            color="primary"
            icon="save"
            :label="txt_lang('save')"
            :loading="loading"
            @click="onOKClick"
            :disable="validName()"
        >
          <template #loading>
            <q-spinner-hourglass color="white"/>
          </template>
        </q-btn>
        <q-btn
            :dense="dense"
            color="primary"
            icon="cancel"
            :label="txt_lang('cancel')"
            @click="onCancelClick"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import {api, baseURL} from "boot/axios";
import {notifyError, notifySuccess, txt_lang} from "src/utils/jsutils";
import {ref} from "vue";

export default {
  props: ["data", "mode", "dense"],

  data() {
    //console.log("data 2", this.data)

    return {
      form: this.data,
      loading: false,

      optionsLevel: [],
      al: this.data.accessLevel,

      optionsStruct: [],
      meterStruct: this.data.meterStruct,

      optionsDeterm: [],
      meterDeterm: this.data.meterDeterm,

      optionsDistribution: [],
      distributionLaw: this.data.distributionLaw,

      optionsByRate: [],
      meterTypeByRate: this.data.meterTypeByRate,

      optionsByPeriod: [],
      meterTypeByPeriod: this.data.meterTypeByPeriod,

      optionsByMember: [],
      meterTypeByMember: this.data.meterTypeByMember,

      optionsBehavior: [],
      meterBehavior: this.data.meterBehavior,

      measures: [],
      measure: this.data.measure,

      tab: ref("main"),
    };
  },

  emits: [
    // REQUIRED
    "ok",
    "hide",
  ],

  methods: {
    txt_lang,
    onBlurName() {
      if (this.form.name) {
        this.form.name = this.form.name.trim();
        if (
            !this.form.fullName ||
            (this.form.fullName && this.form.fullName.trim() === "")
        ) {
          this.form.fullName = this.form.name;
        }
      }
    },

    fnSelMeasure() {
      //console.log("measure", this.measure.id)
      this.form.measure = this.measure.id;
    },

    fnSelectDict(dict) {
      if (dict === "accessLevel") this.form.accessLevel = this.al.id;
      else if (dict === "meterStruct")
        this.form.meterStruct = this.meterStruct.id;
      else if (dict === "meterDeterm")
        this.form.meterDeterm = this.meterDeterm.id;
      else if (dict === "distribution")
        this.form.distributionLaw = this.distributionLaw.id;
      else if (dict === "meterTypeByRate")
        this.form.meterTypeByRate = this.meterTypeByRate.id;
      else if (dict === "meterTypeByPeriod")
        this.form.meterTypeByPeriod = this.meterTypeByPeriod.id;
      else if (dict === "meterTypeByMember")
        this.form.meterTypeByMember = this.meterTypeByMember.id;
      else if (dict === "meterBehavior")
        this.form.meterBehavior = this.meterBehavior.id;
    },

    validName() {
      return !(
          this.form.measure !== undefined &&
          this.form.measure !== null &&
          this.form.measure > 0 &&
          this.form.name !== null &&
          this.form.name !== undefined &&
          this.form.name.trim().length !== 0
      );
    },

    // following method is REQUIRED
    // (don't change its name --> "show")
    show() {
      this.$refs["dialog"]["show"]();
    },

    // following method is REQUIRED
    // (don't change its name --> "hide")
    hide() {
      this.$refs["dialog"]["hide"]();
    },

    onDialogHide() {
      // required to be emitted
      // when QDialog emits "hide" event
      this.$emit("hide");
    },

    onOKClick() {
      // on OK, it is REQUIRED to
      // emit "ok" event (with optional payload)
      // before hiding the QDialog
      this.loading = true
      let err = false
      const method = this.mode === "ins" ? "insert" : "update";
      this.form.lang = localStorage.getItem("curLang");

      api
          .post(baseURL, {
            method: "meter/" + method,
            params: [{rec: this.form}],
          })
          .then(
              (response) => {
                this.$emit("ok", response.data.result.records[0]);
                notifySuccess(this.$t("success"));
              },
              (error) => {
                //console.log("error.response.data=>>>", error.response.data.error.message)
                err = true
                notifyError(error.response.data.error.message);
              }
          )
          .finally(() => {
            this.loading = false
            if (!err)
              this.hide();
          });
    },

    onCancelClick() {
      // we just need to hide the dialog
      this.hide();
    },

    getDict(dictName) {
      const lang = localStorage.getItem("curLang");
      api
          .post(baseURL, {
            method: "dict/load",
            params: [{dict: dictName, lang: lang}],
          })
          .then((response) => {
            if (dictName === "FD_AccessLevel")
              this.optionsLevel = response.data.result.records;
            else if (dictName === "FD_MeterStruct")
              this.optionsStruct = response.data.result.records;
            else if (dictName === "FD_MeterDeterm")
              this.optionsDeterm = response.data.result.records;
            else if (dictName === "FD_DistributionLaw")
              this.optionsDistribution = response.data.result.records;
            else if (dictName === "FD_MeterBehavior")
              this.optionsBehavior = response.data.result.records;
            else if (dictName === "FD_MeterType") {
              this.optionsByRate = response.data.result.records;
              this.optionsByPeriod = response.data.result.records;
              this.optionsByMember = response.data.result.records;
            }
          });
    },
  },
  created() {
    this.getDict("FD_AccessLevel");
    this.getDict("FD_MeterStruct");
    this.getDict("FD_MeterDeterm");
    this.getDict("FD_DistributionLaw");
    this.getDict("FD_MeterBehavior");
    this.getDict("FD_MeterType");
    const lang = localStorage.getItem("curLang");
    api
        .post(baseURL, {
          method: "measure/loadBase",
          params: [{lang: lang}],
        })
        .then((response) => {
          //this.measures = pack(response.data.result.records)
          this.measures = response.data.result.records;
          console.info(this.measures);
        });

    return {};
  },
};
</script>
