workflow "Release" {
  on = "push"
  resolves = ["build-publish"]
}

action "Only Master" {
  uses = "actions/bin/filter@master"
  args = "branch master"
}

action "build-publish" {
  uses = "./actions/build"
  needs = ["Only Master"]
  secrets = ["GRADLE_PLUGIN_PORTAL_KEY", "GRADLE_PLUGIN_PORTAL_SECRET"]
}

workflow "Automated Review" {
  on = "push"
  resolves = ["check"]
}

action "Not Master" {
  uses = "actions/bin/filter@master"
  args = "not branch master"
}

action "check" {
  uses = "./actions/check"
  needs = ["Not Master"]
}
