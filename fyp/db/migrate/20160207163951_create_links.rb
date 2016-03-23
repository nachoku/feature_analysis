class CreateLinks < ActiveRecord::Migration
  def change
    create_table :links do |t|
      t.string :search_id
      t.string :link

      t.timestamps null: false
    end
  end
end
