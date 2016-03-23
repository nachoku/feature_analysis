class CreateStatuses < ActiveRecord::Migration
  def change
    create_table :statuses do |t|
      t.string :user
      t.integer :crawl, null: false, :default =>  0
      t.integer :file_write, null: false, :default =>  1
      t.integer :analysis, null: false, :default => 0
      t.timestamps null: false
    end
  end
end
