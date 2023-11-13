import { MenuRootItem } from "ontimize-web-ngx";

export const MENU_CONFIG: MenuRootItem[] = [
  { id: "home",
   name: "HOME", 
   icon: "home", 
   route: "/main/home" },

  { id: "charts",
   name: "CHARTS", 
   icon: "bar_chart", 
   route: "/main/charts" },
  {
    id: "subscriptions",
    name: "SUBSCRIPTIONS",
    icon: "subscriptions",
    route: "/main/subscriptions",
  },
  {
    id: "plans",
    name: "PLANS",
    icon: "event_note",
    route: "/main/plans",
  },
  {
    id: "categories",
    name: "CATEGORIES",
    icon: "category",
    route: "/main/categories",
  },
  {
    id: "platforms",
    name: "PLATFORMS",
    icon: "stream",
    route: "/main/platforms",
  },
  {
    id: "custom-platforms",
    name: "CUSTOM-PLATFORMS",
    icon: "stream",
    route: "/main/custom-platforms",
  },
  {
    id: "logout",
    name: "LOGOUT",
    route: "/login",
    icon: "power_settings_new",
    confirm: "yes",
  },
  {
    id: "custom-platform",
    name: "CUSTOM_PLATFORMS",
    icon: "Femur",
    route: "/main/custom-platform",
  }
];
