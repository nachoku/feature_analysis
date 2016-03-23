require 'test_helper'

class BackendControllerTest < ActionController::TestCase
  test "should get store" do
    get :store
    assert_response :success
  end

end
