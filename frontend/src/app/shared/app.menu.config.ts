import { MenuRootItem } from "ontimize-web-ngx";

export const MENU_CONFIG: MenuRootItem[] = [
  { id: "home", name: "HOME", icon: "home", route: "/main/home" },
  {
    id: "subscriptions",
    name: "SUBSCRIPTIONS",
    icon: "subscriptions",
    route: "/main/subscriptions",
  },
  {
    id: "categories",
    name: "CATEGORIES",
    icon: "subscriptions",
    route: "/main/categories",
  },
  {
    id: "logout",
    name: "LOGOUT",
    route: "/login",
    icon: "power_settings_new",
    confirm: "yes",
  },
];
