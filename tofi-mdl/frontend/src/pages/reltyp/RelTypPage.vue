<template>
  <q-page class="q-pa-md" style="height: 100px">
    <q-table
        style="height: 100%; width: 100%"
        color="primary"
        card-class="bg-amber-1"
        row-key="id"
        :columns="cols"
        :rows="rows"
        :wrap-cells="true"
        :table-colspan="4"
        table-header-class="text-bold text-white bg-blue-grey-13"
        separator="cell"
        :filter="filter"
        :loading="loading"
        :dense="dense"
        selection="single"
        v-model:selected="selected"
        :rows-per-page-options="[25, 0]"
        @request="requestData"
        v-model:pagination="pagination"
        :max="pagesNumber"
    >
      <template #bottom-row>
        <q-td colspan="100%" v-if="selected.length > 0">
          <span class="text-blue"> {{ txt_lang("selectedRow") }}: </span>
          <span class="text-bold"> {{ this.infoSelected(selected[0]) }} </span>
        </q-td>
        <q-td colspan="100%" v-else-if="this.rows.length > 0" class="text-bold">
          {{ txt_lang("infoRel") }}
        </q-td>
      </template>

      <template v-slot:top>
        <div style="font-size: 1.2em; font-weight: bold;">
          <q-avatar color="black" text-color="white" icon="view_column"></q-avatar>
          {{ txt_lang("reltyps") }}
        </div>

        <q-space/>
        <q-btn
            v-if="hasTarget('mdl:mn_ds:reltyp:ins')"
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
            v-if="hasTarget('mdl:mn_ds:reltyp:upd')"
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
            v-if="hasTarget('mdl:mn_ds:reltyp:del')"
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

        <q-btn
            v-if="hasTarget('mdl:mn_ds:reltyp:sel')"
            style="margin-left: 30px"
            :dense="dense"
            icon="pan_tool_alt"
            color="secondary"
            class="q-ml-sm"
            :disable="loading || selected.length === 0"
            @click="relSelect()"
        >
          <q-tooltip transition-show="rotate" transition-hide="rotate">
            {{ txt_lang("chooseRecord") }}
          </q-tooltip>
        </q-btn>

        <q-toggle
            style="margin-left: 10px"
            :dense="dense"
            v-model="dense"
            :model-value="dense"
            :label="txt_lang('isDense')"
        />

        <q-space/>
        <q-input
            :dense="dense"
            debounce="300"
            color="primary"
            :model-value="filter"
            v-model="filter"
            :label="txt_lang('txt_filter')"
        >
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>
      </template>

      <template #loading>
        <q-inner-loading showing color="secondary"></q-inner-loading>
      </template>
    </q-table>
  </q-page>
</template>

<script>
import {defineComponent, ref} from "vue";
import UpdateRelTyp from "pages/reltyp/UpdateRelTyp.vue";
import {api, baseURL, tofi_dbeg, tofi_dend} from "boot/axios";
import {hasTarget, notifyError, notifyInfo, notifySuccess, txt_lang} from "src/utils/jsutils";
import {date, extend} from "quasar";

const requestParam = {
  page: 1,
  rowsPerPage: 25,
  rowsNumber: 0,
  filter: "",
  descending: false,
  sortBy: null,
};

export default defineComponent({
  name: "RelTypPage",

  data: function () {
    return {
      cols: [],
      rows: [],
      FD_AccessLevel: new Map(),
      filter: "",
      loading: false,
      pagination: {
        sortBy: null,
        descending: false,
        page: 1,
        rowsPerPage: 15,
        rowsNumber: 0,
      },
      selected: [],
      dense: true,

      reltypId: 0
    };
  },

  methods: {
    txt_lang,
    hasTarget,

    relSelect() {
      this.$router["push"]({
        name: "reltypSelected",
        params: {
          reltyp: this.selected[0].id,
          relcls: 0,
          tab: "vers"
        },
      });
    },

    editRow(rec, mode) {
      let data = {
        accessLevel: 1,
        card: 1,
        isOpenness: true,
        lastVer: 1,
      };
      if (mode === "upd") {
        data = {
          id: rec.id,
          cod: rec.cod,
          accessLevel: rec.accessLevel,
          card: rec.card,
          isOpenness: rec.isOpenness,
          lastVer: rec.lastVer,
          dbeg: rec.dbeg > tofi_dbeg ? rec.dbeg : null,
          dend: rec.dend < tofi_dbeg ? rec.dend : null,
          name: rec.name,
          fullName: rec.fullName,
          cmt: rec.cmt,
        };
      }

      this.$q
          .dialog({
            component: UpdateRelTyp,
            componentProps: {
              data: data,
              mode: mode,
              dense: this.dense,
              // ...
            },
          })
          .onOk((r) => {
            //console.log("Ok! updated", r);
            if (mode === "ins") {
              this.rows.push(r);
              this.selected = [];
              this.selected.push(r);
            } else {
              for (let key in r) {
                if (r.hasOwnProperty(key)) {
                  rec[key] = r[key];
                }
              }
            }
          })

    },

    pagesNumber: function () {
      return 1;
    },

    fetchData(requestParam) {
      this.loading = ref(true);
      requestParam.lang = localStorage.getItem("curLang");

      api
          .post(baseURL, {
            method: "reltyp/loadRelTypPaginate",
            params: [requestParam],
          })
          .then(
              (response) => {
                this.rows = response.data.result.store.records;
                const meta = response.data.result.meta;
                this.pagination.page = meta.page;
                this.pagination.rowsPerPage = meta.limit;
                this.pagination.rowsNumber = meta.total;
                this.selected = [];
                //
                if (this.reltypId > 0) {
                  let index = this.rows.findIndex((row) => row.id === this.reltypId);
                  this.selected.push(this.rows[index]);
                }
              },
              (error) => {

                let msg = error.message;
                if (error.response)
                  msg = this.$t(error.response.data.error.message);

                notifyError(msg);
              }
          )
          .finally(() => {
            //setTimeout(() => {
            this.loading = ref(false);
            //}, 500)
          });
    },

    requestData(requestProps) {
      extend(true, requestParam, requestProps.pagination)
      requestParam.filter = requestProps.filter;
      extend(true, this.pagination, requestProps.pagination)
      //
      this.fetchData(requestParam);
    },


    removeRow(rec) {
      //console.log("Delete Row:", JSON.stringify(rec))
      this.$q
          .dialog({
            title: this.$t("confirmation"),
            message:
                this.$t("deleteRecord") +
                '<div style="color: plum">(' +
                rec.cod +
                ": " +
                rec.name +
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
                  method: "reltyp/delete",
                  params: [rec],
                })
                .then(
                    () => {
                      this.rows.splice(index, 1);
                      this.selected = [];
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

    getColumns() {
      return [
        {
          name: "cod",
          label: this.$t("code"),
          field: "cod",
          align: "left",
          sortable: true,
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "width: 10%",
        },
        {
          name: "name",
          label: this.$t("fldName"),
          field: "name",
          align: "left",
          sortable: true,
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "width: 20%",
        },
        {
          name: "fullName",
          label: this.$t("fldFullName"),
          field: "fullName",
          align: "left",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "width: 30%",
        },
        /*
        {
          name: "accessLevel",
          label: this.$t("accessLevel"),
          field: "accessLevel",
          align: "left",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "width: 15%",
          format: val => this.FD_AccessLevel ? this.FD_AccessLevel.get(val) : null,
        },
*/
        {
          name: "dbeg",
          label: this.$t("fldDbegShort"),
          field: "dbeg",
          align: "center",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "width: 10%",
          format: (val) =>
              val <= tofi_dbeg ? "..." : date.formatDate(val, "DD.MM.YYYY"),
        },
        {
          name: "dend",
          label: this.$t("fldDendShort"),
          field: "dend",
          align: "center",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em;",
          style: "width: 10%",
          format: (val) =>
              val >= tofi_dend ? "..." : date.formatDate(val, "DD.MM.YYYY"),
        },
        {
          name: "cmt",
          label: this.$t("fldCmt"),
          field: "cmt",
          align: "left",
          classes: "bg-blue-grey-1",
          headerStyle: "font-size: 1.2em",
          style: "width: 20%",
        },
      ];
    },

    infoSelected(row) {
      return " " + row.cod + " - " + row.name;
    },

  },

  created() {
    const lang = localStorage.getItem("curLang");
    api
        .post(baseURL, {
          method: "dict/load",
          params: [{dict: "FD_AccessLevel", "lang": lang}],
        })
        .then((response) => {
          this.FD_AccessLevel = new Map();
          response.data.result.records.forEach((it) => {
            this.FD_AccessLevel.set(it["id"], it["text"]);
          });
        })
      .finally(()=>{
        this.cols = this.getColumns();
        this.fetchData(requestParam);
      });


  },

  mounted() {
    this.reltypId = parseInt(this.$route["params"].reltyp, 10);
  },

  setup() {
  }

});
</script>

<style scoped></style>
