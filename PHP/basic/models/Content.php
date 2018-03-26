<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "content".
 *
 * @property integer $id
 * @property integer $contenttype
 * @property string $serverpath
 * @property string $clientpath
 * @property string $filename
 */
class Content extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'content';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['contenttype'], 'integer'],
            [['serverpath', 'clientpath', 'filename'], 'string', 'max' => 100]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'contenttype' => 'Contenttype',
            'serverpath' => 'Serverpath',
            'clientpath' => 'Clientpath',
            'filename' => 'Filename',
        ];
    }
}
