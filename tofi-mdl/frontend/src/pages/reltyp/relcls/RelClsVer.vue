<template>
  <div class="no-padding no-margin">
    <q-splitter
        v-model="splitterModel"
        :model-value="splitterModel.value"
        before-class="overflow-hidden q-mr-sm"
        after-class="overflow-hidden q-ml-sm"
        separator-class="bg-red"
    >
      <template v-slot:before>
        <div>
          <q-table
              style="height: calc(100vh - 250px); width: 100%"
              color="primary"
              card-class="bg-amber-1"
              row-key="id"
              :columns="cols"
              :rows="rows"
              :wrap-cells="true"
              table-header-class="text-bold text-white bg-blue-grey-13"
              separator="cell"
              :loading="loading"
              :dense="dense"
              selection="single"
              v-model:selected="selected"
              :rows-per-page-options="[0]"
          >
            <template #bottom-row>
              <q-td colspan="100%" v-if="selected.length > 0">
                <span class="text-blue"> {{ txt_lang("selectedRow") }}: </span>
                <span class="text-bold"> {{ this.infoSelected(selected[0]) }} </span>
              </q-td>
              <q-td
                  colspan="100%"
                  v-else-if="this.rows.length > 0"
                  class="text-bold"
              >
                {{ txt_lang("infoVer") }}
              </q-td>
            </template>

            <template v-slot:top>
              <div style="font-size: 1.2em; font-weight: bold;">{{ txt_lang("vers") }}</div>

              <q-space/>
              <q-btn
                  v-if="hasTarget('mdl:mn_ds:reltyp:sel:ver:ins')"
                  :dense="dense"
                  icon="post_add"
                  color="secondary"
                  :disable="loading"
                  @click="editRow(null, 'ins')"
              >
                <q-tooltip transition-show="rotate" transition-hide="rotate">
                  {{ txt_lang("newRecord") }}
                </q-tooltip>
              </q-btn>
              <q-btn
                  v-if="hasTarget('mdl:mn_ds:reltyp:sel:ver:upd')"
                  :dense="dense"
                  icon="edit"
                  color="secondary"
                  class="q-ml-sm"
                  :disable="loading || selected.length === 0"
                  @click="editRow(selected[0], 'upd')"
              >
                <q-tooltip transition-show="rotate" transition-hide="rotate">
                  {{ txt_lang("editRecord") }}
                </q-tooltip>
              </q-btn>
              <q-btn
                  v-if="hasTarget('mdl:mn_ds:reltyp:sel:ver:del')"
                  :dense="dense"
                  icon="delete"
                  color="secondary"
                  class="q-ml-sm"
                  :disable="loading || selected.length === 0"
                  @click="removeRow(selected[0])"
              >
                <q-tooltip transition-show="rotate" transition-hide="rotate">
                  {{ txt_lang("deletingRecord") }}
                </q-tooltip>
              </q-btn>
            </template>

            <template #loading>
              <q-inner-loading :showing="loading" color="secondary"></q-inner-loading>
            </template>
          </q-table>
        </div>
      </template>

      <template v-slot:after>
        <div
            class="q-pa-md bg-amber-1"
            style="height: calc(100vh - 250px); width: 100%"
        >
          <div style="font-size: 1.2em; font-weight: bold;">
            {{ txt_lang("valueOfProp") }}
          </div>
          <hr>
          <div class="text-blue">{{ txt_lang("fldName") }}:</div>
          <div class="text-weight-bold">{{ getName() }}</div>
          <div class="text-blue">{{ txt_lang("fldFullName") }}:</div>
          <div class="text-weight-bold">{{ getFullName() }}</div>
          <div class="text-blue">{{ txt_lang("fldCmt") }}:</div>
          <div class="text-weight-bold">{{ getCmt() }}</div>
          <div class="text-blue">{{ txt_lang("life") }}:</div>
          <div class="text-weight-bold">{{ getLife() }}</div>
        </div>
      </template>
    </q-splitter>
  </div>
</template>

<script>
import {ref} from "vue";
import {api, baseURL} from "boot/axios";
import {date} from "quasar";
import {hasTarget, notifyError, notifyInfo, notifySuccess, txt_lang} from "src/utils/jsutils";
import UpdateRelClsVer from "pages/reltyp/relcls/UpdateRelClsVer.vue";

export default {
  name: "RelClsVer",

  emits: [
    "loadRelCls"
  ],

  data() {
    //console.log("data")
    return {
      splitterModel: 30,
      cols: [],
      rows: [],
      FD_AccessLevel: null,
      loading: false,
      selected: [],
      dense: true,
      relclsId: 0,
    };
  },

  methods: {
    txt_lang,
    hasTarget,
    removeRow(rec) {
      //console.log("Delete Row:", JSON.stringify(rec))
      let d1 =
          rec.dbeg === "1800-01-01"
              ? "..."
              : date.formatDate(rec.dbeg, "DD.MM.YYYY");
      let d2 =
          rec.dend === "3333-12-31"
              ? "..."
              : date.formatDate(rec.dend, "DD.MM.YYYY");

      this.$q
          .dialog({
            title: this.$t("confirmation"),
            message:
                this.$t("deleteRecord") +
                '<div style="color: plum">(' +
                d1 +
                " - " +
                d2 +
                ")</div>",
            html: true,
            cancel: true,
            persistent: true,
            focus: "cancel",
          })
          .onOk(() => {
            let index = this.rows.findIndex((row) => row.id === rec.id);
            api
                .post(baseURL, {
                  method: "relcls/deleteVer",
                  params: [rec],
                })
                .then(
                    () => {
                      //console.log("response=>>>", response.data)
                      this.rows.splice(index, 1);
                      this.selected = ref([]);

                      this.$emit("loadRelCls", this.relclsId);

                      notifySuccess(this.$t("success"));
                    },
                    (error) => {
                      let msg = error.message;
                      if (error.response) msg = error.response.data.error.message;

                      notifyError(msg);
                    }
                );
          })
          .onCancel(() => {
            notifyInfo(this.$t("canceled"));
          });
    },

    editRow(rec, mode) {
      let data = {
        id: 0,
        ownerVer: this.relclsId,
        name: "",
        fullName: "",
        cmtVer: null,
      };
      if (mode === "upd") {
        data = {
          id: rec.id,
          ownerVer: rec.ownerVer,
          name: rec.name,
          fullName: rec.fullName,
          dbeg: rec.dbeg > "1800-01-01" ? rec.dbeg : null,
          dend: rec.dend < "3333-12-31" ? rec.dend : null,
          cmtVer: rec.cmtVer,
        };
      }

      const lg = {name: this.lang};

      this.$q
          .dialog({
            component: UpdateRelClsVer,
            componentProps: {
              data: data,
              mode: mode,
              lg: lg,
              dense: true,
              // ...
            },
          })
          .onOk((r) => {
            //console.log("Ok! updated", r);
            if (mode === "ins") {
              this.rows.unshift(r);
              this.selected = [];
              this.selected.push(r);

              this.$emit("loadRelCls", this.relclsId);
            } else {
              for (let key in r) {
                if (r.hasOwnProperty(key)) {
                  rec[key] = r[key];
                }
              }
            }
          })
    },

    getName() {
      if (this.selected.length > 0) return this.selected[0].name;
      else return "...";
    },
    getFullName() {
      if (this.selected.length > 0) return this.selected[0].fullName;
      else return "...";
    },
    getCmt() {
      if (this.selected.length > 0)
        return this.selected[0].cmtVer ? this.selected[0].cmtVer : "-"
      else
        return "...";
    },

    getLife() {
      if (this.selected.length > 0) {
        let d1 =
            this.selected[0].dbeg <= "1800-01-01"
                ? "..."
                : date.formatDate(this.selected[0].dbeg, "DD.MM.YYYY");
        let d2 =
            this.selected[0].dend >= "3333-12-31"
                ? "..."
                : date.formatDate(this.selected[0].dend, "DD.MM.YYYY");
        return d1 + " - " + d2;
      } else return "...";
    },

    fetchData(relcls) {
      this.loading = false;
      api
          .post(baseURL, {
            method: "relcls/loadVer",
            params: [relcls],
          })
          .then((response) => {
            this.rows = response.data.result.records;
          })
          .catch((error) => {
            let msg = error.message;
            if (error.response) msg = this.$t(error.response.data.error.message);

            notifyError(msg);
          })
          .finally(() => {
            this.loading = false
          });
    },

    getColumns() {
      return [
        {
          name: "dbeg",
          label: this.$t("fldDbeg"),
          field: "dbeg",
          align: "center",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "width: 90px",
          format: (val) =>
              val <= "1800-01-01" ? "..." : date.formatDate(val, "DD.MM.YYYY"),
        },
        {
          name: "dend",
          label: this.$t("fldDend"),
          field: "dend",
          align: "center",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em;",
          style: "width: 90px",
          format: (val) =>
              val >= "3333-12-01" ? "..." : date.formatDate(val, "DD.MM.YYYY"),
        },
      ];
    },

    infoSelected(row) {
      let d1 =
          row.dbeg === "1800-01-01"
              ? "..."
              : date.formatDate(row.dbeg, "DD.MM.YYYY");
      let d2 =
          row.dend === "3333-12-31"
              ? "..."
              : date.formatDate(row.dend, "DD.MM.YYYY");
      return " " + d1 + " - " + d2;
    },
  },

  created() {
    //console.log("create")
    this.lang = localStorage.getItem("curLang");
    this.lang = this.lang === "en-US" ? "en" : this.lang;

    this.cols = this.getColumns();
  },

  mounted() {
    //console.log("mounted")
    this.relclsId = parseInt(this.$route["params"].relcls, 10);
    this.fetchData(this.relclsId);
  },

  setup() {
  }

};
</script>

<style scoped></style>
