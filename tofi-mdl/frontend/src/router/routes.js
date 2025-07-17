const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {path: "", component: () => import("pages/IndexPage.vue")},
      {path: "/dbSetting", component: () => import("pages/IndexPage.vue")},
      {path: "/database", component: () => import("pages/database/DataBasePage.vue")},

      {path: "/dataSetting", component: () => import("pages/IndexPage.vue")},
      {
        path: "/measure",
        component: () => import("pages/measure/MeasurePage.vue"),
      },
      {
        path: "/attrib",
        component: () => import("pages/attrib/AttribPage.vue"),
      },

      {
        path: "/factor/:factor",
        name: "FactorPage",
        component: () => import("pages/factor/FactorPage.vue"),
      },
      {
        path: "/factor/:factor",
        name: "factorValMain",
        component: () => import("pages/factor/FactorValMain.vue"),
      },
      {
        path: "/meter/:meter",
        name: "meterPage",
        component: () => import("pages/meter/MeterPage.vue")
      },
      {
        path: "/meter/:meter/:meterStruct",
        name: "meterSelected",
        component: () => import("pages/meter/MeterSelected.vue"),
      },

      {path: "/role", component: () => import("pages/role/RolePage.vue")},

      {
        path: "/typ/:typ",
        name: "typPage",
        component: () => import("pages/typ/TypPage.vue")
      },
      {
        path: "/typ/:typ/:tab/:cls",
        name: "typSelected",
        component: () => import("pages/typ/TypSelected.vue"),
      },
      {
        path: "/cls/:cls",
        name: "clsVer",
        component: () => import("pages/typ/cls/ClsVer.vue"),
      },

      {
          path: "/reltyp/:reltyp",
          name: "reltypPage",
          component: () => import("pages/reltyp/RelTypPage.vue"),
      },
      {
          path: "/reltyp/:reltyp/:relcls/:tab/",
          name: "reltypSelected",
          component: () => import("pages/reltyp/RelTypSelected.vue"),
      },

      {
          path: "/relcls/:reltyp/:relcls/:tab",
          name: "relclsSelected",
          component: () => import("pages/reltyp/relcls/RelClsSelected.vue"),
      },

      {
          path: "/props/:propGr/:prop",
          name: "propPage",
          component: () => import("pages/prop/PropPage.vue")
      },

      {
          path: "/props/:propGr/:prop",
          name: "propSelected",
          component: () => import("pages/prop/PropSelected.vue"),
      },


      /*


            {
              path: "/impObj",
              component: () => import("pages/instruments/import/obj/ImportObj.vue"),
            },
            {
              path: "/impFile",
              component: () => import("pages/instruments/import/ImportFile.vue"),
            },
      */

    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
